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
import com.mapbox.navigation.core.MapboxNavigationProvider
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineViewOptions
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineColorResources

@Composable
internal fun RouteView(
    mapView: MapView,
    state: HomeState,
    onRouteResult: (result: RouteResult) -> Unit
) {
    val mapBoxRouteInitOptions = rememberMapRouteInitOptions(
        mapView = mapView,
        routeLineViewOptions = getRouteLineViewOptions(mapView.context),
    )

    val mapboxNavigation = remember(mapView) {
        MapboxNavigationProvider.create(
            NavigationOptions.Builder(mapView.context).build()
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
        MapboxNavigationProvider.destroy()
    }
}

private fun getRouteLineViewOptions(context: Context): MapboxRouteLineViewOptions {
    val color = context.getColor(R.color.color_sea_serpent)
    val customColorResources = RouteLineColorResources.Builder()
        .routeDefaultColor(color)
        .routeCasingColor(color)
        .routeUnknownCongestionColor(color)
        .build()

    return MapboxRouteLineViewOptions.Builder(context)
        .routeLineBelowLayerId(LOCATION_INDICATOR_LAYER)
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
    val exactUserLocation = (state.locationState as? LocationState.ExactLocation)?.location?.toPoint()
    val feedingPointLocation = state.feedState.feedPoint?.coordinates

    if (exactUserLocation != null && feedingPointLocation != null) {
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
