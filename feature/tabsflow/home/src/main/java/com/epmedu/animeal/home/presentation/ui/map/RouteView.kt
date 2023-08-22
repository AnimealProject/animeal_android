package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.doOnDetach
import com.epmedu.animeal.feeding.presentation.model.MapLocation.Companion.toPoint
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingState
import com.epmedu.animeal.home.presentation.model.MapPath
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.router.model.RouteResult
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.epmedu.animeal.timer.data.model.TimerState
import com.mapbox.maps.MapView
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.NavigationRoute
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineResources

@Composable
internal fun RouteView(
    mapView: MapView,
    state: HomeState,
    onRouteResult: (result: RouteResult) -> Unit
) {
    var mapBoxRouteInitOptions by remember(mapView) {
        mutableStateOf(getMapBoxNavigationInitOptions(mapView, state))
    }

    if (state.feedingRouteState is FeedingRouteState.Disabled) {
        mapView.removeRoute(mapBoxRouteInitOptions)
    }

    remember(state.feedState.feedPoint?.getDrawableRes()) {
        mapBoxRouteInitOptions = getMapBoxNavigationInitOptions(mapView, state)
        null
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
        LaunchedEffect(key1 = feedingRouteState) {
            when {
                gpsSettingState == GpsSettingState.Disabled && currentFeedingPoint != null -> {
                    currentFeedingPoint.let { mapView.focusOnFeedingPoint(it) }
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
    state.feedState.feedPoint?.coordinates?.let { feedingPointLocation ->
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

private fun getMapBoxNavigationInitOptions(
    mapView: MapView,
    state: HomeState
): MapBoxRouteInitOptions {
    val routeLineResources = RouteLineResources.Builder()
        .originWaypointIcon(R.drawable.ic_your_location)

    state.feedState.feedPoint?.getDrawableRes()?.let { feedingPointDrawable ->
        routeLineResources.destinationWaypointIcon(feedingPointDrawable)
    }

    return MapBoxRouteInitOptions(
        MapboxRouteLineOptions.Builder(mapView.context)
            .withRouteLineResources(
                routeLineResources
                    .build()
            )
            .build()
    )
}