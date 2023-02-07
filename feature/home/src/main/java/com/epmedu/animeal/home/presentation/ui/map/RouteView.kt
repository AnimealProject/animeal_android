package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.view.doOnDetach
import com.epmedu.animeal.feeding.presentation.model.MapLocation.Companion.toPoint
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.model.MapPath
import com.epmedu.animeal.home.presentation.model.RouteResult
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.mapbox.maps.MapView
import com.mapbox.navigation.base.options.NavigationOptions
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

    LaunchedEffect(key1 = state.feedingRouteState) {
        when (state.feedingRouteState) {
            FeedingRouteState.Disabled -> {
                mapView.removeRoute(mapBoxRouteInitOptions)
            }
            is FeedingRouteState.Active -> {
                if (state.feedingRouteState.routeData != null) {
                    drawRoute(state, mapView, mapBoxRouteInitOptions)
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

    // App crashes if we don't destroy mapNavigation onDetach
    mapView.doOnDetach {
        mapboxNavigation.onDestroy()
    }
}

internal fun setLocationOnRoute(mapView: MapView, state: HomeState) {
    state.currentFeedingPoint?.location?.let { feedingPointLocation ->
        mapView.setLocation(
            points = listOf(
                state.locationState.location.toPoint(),
                feedingPointLocation.toPoint()
            )
        )
    }
}

private fun drawRoute(
    state: HomeState,
    mapView: MapView,
    mapBoxRouteInitOptions: MapBoxRouteInitOptions
) {
    state.feedingRouteState.routeData?.let {
        mapView.drawRoute(mapBoxRouteInitOptions, it)
    }
    if (mapView.getMapboxMap().getStyle()?.isStyleLoaded == true) {
        setLocationOnRoute(mapView, state)
    }
}

private fun fetchRoute(
    state: HomeState,
    mapView: MapView,
    mapBoxRouteInitOptions: MapBoxRouteInitOptions,
    mapboxNavigation: MapboxNavigation,
    onRouteResult: (result: RouteResult) -> Unit
) {
    state.currentFeedingPoint?.location?.let { feedingPointLocation ->
        mapView.fetchRoute(
            mapBoxRouteInitOptions,
            mapboxNavigation,
            MapPath(
                state.locationState.location.toPoint(),
                feedingPointLocation.toPoint()
            ),
            onRouteResult = onRouteResult
        )
    }
}