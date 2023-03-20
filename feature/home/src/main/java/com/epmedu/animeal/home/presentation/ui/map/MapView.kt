@file:OptIn(FlowPreview::class)

package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.doOnDetach
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.home.presentation.model.MapPath
import com.epmedu.animeal.home.presentation.model.RouteResult
import com.epmedu.animeal.home.utils.MapConstants.DEFAULT_MAP_BOTTOM_PADDING
import com.epmedu.animeal.home.utils.MapConstants.DEFAULT_MAP_END_PADDING
import com.epmedu.animeal.home.utils.MapConstants.DEFAULT_MAP_START_PADDING
import com.epmedu.animeal.home.utils.MapConstants.DEFAULT_MAP_TOP_PADDING
import com.epmedu.animeal.home.utils.MapConstants.DEFAULT_ZOOM
import com.epmedu.animeal.resources.R
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.ResourceOptions
import com.mapbox.maps.extension.observable.eventdata.CameraChangedEventData
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.extensions.coordinates
import com.mapbox.navigation.base.route.NavigationRoute
import com.mapbox.navigation.base.route.NavigationRouterCallback
import com.mapbox.navigation.base.route.RouterFailure
import com.mapbox.navigation.base.route.RouterOrigin
import com.mapbox.navigation.core.MapboxNavigation
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce

private const val GESTURE_INTERACTION_DEBOUNCE = 100L

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
            compass.enabled = uiSettings.compassEnabled

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

fun MapView.fetchRoute(
    mapBoxRouteInitOptions: MapBoxRouteInitOptions,
    navigation: MapboxNavigation,
    path: MapPath,
    onRouteResult: (result: RouteResult) -> Unit
) {
    val routeOptions = RouteOptions.builder()
        .applyDefaultNavigationOptions()
        .profile(DirectionsCriteria.PROFILE_WALKING) // Assuming people will walk to their destinations...
        .coordinates(origin = path.origin, destination = path.destination)
        .build()

    requestRoutes(navigation, routeOptions, onRouteResult)

    doOnDetach {
        mapBoxRouteInitOptions.run {
            routeLineApi.cancel()
            routeLineView.cancel()
        }
    }
}

fun MapView.focusOnFeedingPoint(
    feedingPoint: FeedingPointModel
) {
    setLocation(
        location = MapLocation(
            feedingPoint.coordinates.latitude(),
            feedingPoint.coordinates.longitude()
        ),
        zoom = DEFAULT_ZOOM
    )
}

fun MapView.drawRoute(
    mapBoxRouteInitOptions: MapBoxRouteInitOptions,
    route: NavigationRoute
) {
    mapBoxRouteInitOptions.routeLineApi.setNavigationRoutes(listOf(route)) { value ->
        getMapboxMap().getStyle()?.let { style ->
            mapBoxRouteInitOptions.routeLineView.renderRouteDrawData(style, value)
        }
    }
}

fun MapView.removeRoute(mapBoxRouteInitOptions: MapBoxRouteInitOptions) {
    mapBoxRouteInitOptions.run {
        if (routeLineApi.getNavigationRoutes().isNotEmpty()) {
            routeLineApi.clearRouteLine { value ->
                getMapboxMap().getStyle()?.let { style ->
                    routeLineView.renderClearRouteLineValue(style, value)
                }
            }
        }
    }
}

@Composable
fun MapView.GesturesListener(onMapInteraction: () -> Unit) {
    val debounceState = remember {
        MutableSharedFlow<CameraChangedEventData>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    LaunchedEffect(true) {
        debounceState
            .debounce(GESTURE_INTERACTION_DEBOUNCE)
            .collect {
                onMapInteraction()
            }
    }

    getMapboxMap().addOnCameraChangeListener {
        debounceState.tryEmit(it)
    }
}

private fun MapView.requestRoutes(
    navigation: MapboxNavigation,
    routeOptions: RouteOptions,
    onRouteResult: (result: RouteResult) -> Unit
) {
    navigation.requestRoutes(
        routeOptions,
        object : NavigationRouterCallback {
            override fun onRoutesReady(routes: List<NavigationRoute>, routerOrigin: RouterOrigin) {
                // Mapbox always considers the first route the better one
                routes.firstOrNull()?.directionsResponse?.routes()?.firstOrNull()?.run {
                    onRouteResult(
                        RouteResult(
                            true,
                            distanceLeft = distance().toLong(),
                            timeLeft = duration().toLong(),
                            routeData = routes.first()
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
}