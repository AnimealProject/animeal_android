package com.epmedu.animeal.home.presentation.ui.map

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.view.doOnDetach
import com.epmedu.animeal.feeding.presentation.model.MapLocation.Companion.toPoint
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingState
import com.epmedu.animeal.home.presentation.model.MapPath
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.LocationState
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.router.model.RouteResult
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.NavigationRoute
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineColorResources
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineResources

@Composable
internal fun RouteView(
    mapView: MapView,
    state: HomeState,
    onRouteResult: (result: RouteResult) -> Unit
) {
    val mapBoxRouteInitOptions = rememberMapRouteInitOptions(
        mapView = mapView,
        mapBoxNavigationInitOptions = MapBoxRouteInitOptions(
            MapboxRouteLineOptions.Builder(mapView.context)
                .withRouteLineResources(getRouteLineResources(mapView.context))
                .withRouteLineBelowLayerId(LOCATION_INDICATOR_LAYER)
                .build()
        )
    )

    LaunchedEffect(mapView) {
        mapView.getMapboxMap().getStyle()?.let { style ->
            mapBoxRouteInitOptions.routeLineView.hideOriginAndDestinationPoints(style)
        }
    }

    val mapboxNavigation = remember(mapView) {
        MapboxNavigation(
            NavigationOptions.Builder(mapView.context)
                .accessToken(mapView.getMapboxMap().getResourceOptions().accessToken)
                .build()
        )
    }

    with(state) {
        val feedingRouteState = feedingRouteState
        val currentFeedingPoint = feedState.feedPoint
        val exactUserLocationAcquired = locationState is LocationState.ExactLocation
        LaunchedEffect(feedingRouteState, exactUserLocationAcquired) {
            when {
                gpsSettingState == GpsSettingState.Disabled && currentFeedingPoint != null -> {
                    currentFeedingPoint.let { mapView.focusOnFeedingPoint(it) }
                }
                feedingRouteState is FeedingRouteState.Disabled -> {
                    mapView.removeRoute(mapBoxRouteInitOptions)
                }
                feedingRouteState is FeedingRouteState.Active -> {
                    if (feedingRouteState.routeData != null) {
                        feedingRouteState.routeData?.let { data ->
                            drawRoute(data, mapView, mapBoxRouteInitOptions)
                        }
                        if (feedingRouteState.showFullRoad) {
                            setLocationOnRoute(mapView, state)
                        }
                    } else {
                        fetchRoute(
                            state,
                            mapView,
                            mapBoxRouteInitOptions,
                            mapboxNavigation,
                            onRouteResult
                        )
                    }
                }
            }
        }
    }

    // App crashes if we don't destroy mapNavigation onDetach
    mapView.doOnDetach {
        mapboxNavigation.onDestroy()
    }
}

@Composable
private fun getRouteLineResources(context: Context): RouteLineResources {
    val color = context.getColor(R.color.color_sea_serpent)
    val customColorResources = RouteLineColorResources.Builder()
        .routeDefaultColor(color)
        .routeCasingColor(color)
        .routeUnknownCongestionColor(color)
        .build()

    return RouteLineResources.Builder()
        .routeLineColorResources(customColorResources)
        .build()
}

internal fun setLocationOnRoute(mapView: MapView, state: HomeState) {
    state.feedState.feedPoint?.coordinates?.let { feedingPointLocation ->
        mapView.setLocation(
            points = listOf(
                state.locationState.location.toPoint(),
                feedingPointLocation
            )
        )
    }
}

private fun drawRoute(
    routeData: NavigationRoute,
    mapView: MapView,
    mapBoxRouteInitOptions: MapBoxRouteInitOptions
) {
    mapView.drawRoute(mapBoxRouteInitOptions, routeData)
}

private fun fetchRoute(
    state: HomeState,
    mapView: MapView,
    mapBoxRouteInitOptions: MapBoxRouteInitOptions,
    mapboxNavigation: MapboxNavigation,
    onRouteResult: (result: RouteResult) -> Unit
) {
    (state.locationState as? LocationState.ExactLocation)?.location?.toPoint()?.let { exactUserLocation ->
        state.feedState.feedPoint?.coordinates?.let { feedingPointLocation ->
            mapView.fetchRoute(
                mapBoxRouteInitOptions = mapBoxRouteInitOptions,
                navigation = mapboxNavigation,
                path = MapPath(
                    exactUserLocation,
                    feedingPointLocation
                ),
                onRouteResult = onRouteResult
            )
        }
    }
}
