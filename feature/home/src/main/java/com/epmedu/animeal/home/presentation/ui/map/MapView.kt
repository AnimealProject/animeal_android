package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.doOnDetach
import com.epmedu.animeal.common.constants.MapView.DEFAULT_MAP_BOTTOM_PADDING
import com.epmedu.animeal.common.constants.MapView.DEFAULT_MAP_END_PADDING
import com.epmedu.animeal.common.constants.MapView.DEFAULT_MAP_START_PADDING
import com.epmedu.animeal.common.constants.MapView.DEFAULT_MAP_TOP_PADDING
import com.epmedu.animeal.common.constants.MapView.DEFAULT_ZOOM
import com.epmedu.animeal.extensions.formatMetersToKilometers
import com.epmedu.animeal.extensions.formatNumberToHourMin
import com.epmedu.animeal.home.presentation.model.MapLocation
import com.epmedu.animeal.home.presentation.model.RouteResult
import com.epmedu.animeal.resources.R
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.extensions.coordinates
import com.mapbox.navigation.base.route.NavigationRoute
import com.mapbox.navigation.base.route.NavigationRouterCallback
import com.mapbox.navigation.base.route.RouterFailure
import com.mapbox.navigation.base.route.RouterOrigin
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView

@Composable
fun rememberMapViewWithLifecycle(
    mapBoxInitOptions: MapBoxInitOptions,
    uiSettings: MapUiSettings
): MapView {
    val context = LocalContext.current

    val mapInitOptions = MapInitOptions(
        context = context,
        resourceOptions = ResourceOptions.Builder()
            .accessToken(mapBoxInitOptions.publicKey)
            .build(),
        styleUri = mapBoxInitOptions.styleUrl
    )

    val mapView = remember(mapBoxInitOptions, uiSettings) {
        MapView(context, mapInitOptions).apply {
            scalebar.enabled = uiSettings.scalebar

            location.updateSettings {
                enabled = uiSettings.userLocationOnMap
                pulsingEnabled = uiSettings.locationPulsing
            }
        }
    }

    // TODO: Use Activity lifecycle to avoid memory leaks
    DisposableEffect(mapView) {
        onDispose {
            mapView.onDestroy()
        }
    }

    return mapView
}

fun MapView.setLocation(location: MapLocation, zoom: Double = DEFAULT_ZOOM) =
    getMapboxMap().setCamera(
        CameraOptions.Builder()
            .zoom(zoom)
            .center(Point.fromLngLat(location.longitude, location.latitude))
            .build()
    )

fun MapView.setLocation(
    points: List<Point>,
    padding: EdgeInsets = EdgeInsets(
        DEFAULT_MAP_TOP_PADDING,
        DEFAULT_MAP_START_PADDING,
        DEFAULT_MAP_BOTTOM_PADDING,
        DEFAULT_MAP_END_PADDING
    )
) {
    val cameraPosition = getMapboxMap().cameraForCoordinates(points, padding)
    getMapboxMap().setCamera(cameraPosition)
}

fun MapView.drawRoute(
    mapboxNavigation: MapboxNavigation,
    routeLineApi: MapboxRouteLineApi,
    routeLineView: MapboxRouteLineView,
    origin: Point,
    destination: Point,
    onRouteResult: (result: RouteResult) -> Unit
) {
    val routeOptions = RouteOptions.builder()
        .applyDefaultNavigationOptions()
        .profile(DirectionsCriteria.PROFILE_WALKING) // Assuming people will walk to their destinations...
        .coordinates(origin = origin, destination = destination)
        .build()

    mapboxNavigation.requestRoutes(
        routeOptions,
        object : NavigationRouterCallback {
            override fun onRoutesReady(routes: List<NavigationRoute>, routerOrigin: RouterOrigin) {
                routeLineApi.setNavigationRoutes(routes) { value ->
                    getMapboxMap().getStyle()
                        ?.let { style -> routeLineView.renderRouteDrawData(style, value) }
                }

                // Mapbox always considers the first route the better one
                routes.firstOrNull()?.directionsResponse?.routes()?.firstOrNull()?.run {
                    onRouteResult(
                        RouteResult(
                            true,
                            distanceLeft = formatMetersToKilometers(distance().toLong()),
                            timeLeft = formatNumberToHourMin(duration().toLong())
                        )
                    )
                    return
                }

                onRouteResult(
                    RouteResult(
                        false,
                        errorMessage = context.getString(R.string.routes_not_found)
                    )
                )
            }

            override fun onFailure(reasons: List<RouterFailure>, routeOptions: RouteOptions) {
                onRouteResult(
                    RouteResult(
                        false,
                        errorMessage = context.getString(R.string.routes_failed)
                    )
                )
            }

            override fun onCanceled(routeOptions: RouteOptions, routerOrigin: RouterOrigin) {
                onRouteResult(
                    RouteResult(
                        false,
                        errorMessage = context.getString(R.string.routes_cancelled)
                    )
                )
            }
        }
    )

    doOnDetach {
        routeLineApi.cancel()
        routeLineView.cancel()
    }
}

fun MapView.removeRoute(routeLineApi: MapboxRouteLineApi, routeLineView: MapboxRouteLineView) {
    routeLineApi.clearRouteLine { value ->
        getMapboxMap().getStyle()?.let { style ->
            routeLineView.renderClearRouteLineValue(style, value)
        }
    }
}