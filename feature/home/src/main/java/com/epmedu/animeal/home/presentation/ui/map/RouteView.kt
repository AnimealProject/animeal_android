package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.view.doOnDetach
import com.epmedu.animeal.feeding.presentation.model.MapLocation.Companion.toPoint
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.model.MapPath
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.router.model.RouteResult
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.epmedu.animeal.timer.data.model.TimerState
import com.mapbox.maps.MapView
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.NavigationRoute
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions

@Composable
internal fun RouteView(
    mapView: MapView,
    state: HomeState,
    onRouteResult: (result: RouteResult) -> Unit
) {
    val mapBoxRouteInitOptions = rememberMapRouteInitOptions(
        mapView = mapView,
        mapBoxNavigationInitOptions = MapBoxRouteInitOptions(
            MapboxRouteLineOptions.Builder(mapView.context).build()
        )
    )

    val mapboxNavigation = remember(mapView) {
        MapboxNavigation(
            NavigationOptions.Builder(mapView.context)
                .accessToken(mapView.getMapboxMap().getResourceOptions().accessToken)
                .build()
        )
    }

    with(state) {
        val feedingRouteState = feedingPointState.feedingRouteState
        val currentFeedingPoint = feedingPointState.currentFeedingPoint
        LaunchedEffect(key1 = feedingRouteState) {
            when {
                gpsSettingState is GpsSettingState.Disabled && currentFeedingPoint != null -> {
                    currentFeedingPoint.let { mapView.focusOnFeedingPoint(it) }
                }
                feedingRouteState is FeedingRouteState.Disabled -> {
                    mapView.removeRoute(mapBoxRouteInitOptions)
                }
                feedingRouteState is FeedingRouteState.Active &&
                    timerState is TimerState.Active -> {
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

internal fun setLocationOnRoute(mapView: MapView, state: HomeState) {
    state.feedingPointState.currentFeedingPoint?.coordinates?.let { feedingPointLocation ->
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
    state.feedingPointState.currentFeedingPoint?.coordinates?.let { feedingPointLocation ->
        mapView.fetchRoute(
            mapBoxRouteInitOptions = mapBoxRouteInitOptions,
            navigation = mapboxNavigation,
            path = MapPath(
                state.locationState.location.toPoint(),
                feedingPointLocation
            ),
            onRouteResult = onRouteResult
        )
    }
}