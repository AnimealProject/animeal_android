package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.doOnDetach
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.model.MapLocation.Companion.toPoint
import com.epmedu.animeal.home.presentation.model.RouteResult
import com.epmedu.animeal.home.presentation.ui.map.*
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.delegates.listeners.OnStyleLoadedListener
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions

@Composable
fun MapboxMap(
    state: HomeState,
    onFeedingPointClick: (point: FeedingPointUi) -> Unit,
    onRouteResult: (result: RouteResult) -> Unit
) {
    val mapView = rememberMapViewWithLifecycle(
        mapBoxInitOptions = rememberMapInitOptions(
            MapBoxInitOptions(
                publicKey = state.mapBoxPublicKey,
                styleUrl = state.mapBoxStyleUri
            )
        ),
        uiSettings = rememberMapUiSettings(
            MapUiSettings(
                scalebar = false,
                userLocationOnMap = true
            )
        )
    )

    SetUpRoute(mapView = mapView, state = state, onRouteResult = onRouteResult)

    val markerController = remember(mapView) {
        MarkerController(
            mapView = mapView,
            onFeedingPointClick = onFeedingPointClick
        )
    }

    LaunchedEffect(key1 = state.feedingPoints) {
        markerController.drawMarkers(
            feedingPoints = state.feedingPoints
        )
    }

    LaunchedEffect(key1 = state.currentLocation) {
        mapView.setLocation(state.currentLocation)
    }

    // If we return from other tab and there was a route active in map the camera zoom will not work
    // so we have to make sure the map is loaded before setting location
    val onStyleLoadedListener = OnStyleLoadedListener { event ->
        event.end?.run {
            if (state.feedingRouteState.isRouteActive) {
                setLocationOnRoute(mapView, state)
            } else {
                mapView.setLocation(state.currentLocation)
            }
        }
    }
    // TODO check if it is needed to remove, probably yes
    mapView.getMapboxMap().addOnStyleLoadedListener(onStyleLoadedListener)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { mapView }
    )
}

@Composable
private fun SetUpRoute(
    mapView: MapView,
    state: HomeState,
    onRouteResult: (result: RouteResult) -> Unit
) {
    val mapboxNavigation = remember(mapView) {
        MapboxNavigation(
            NavigationOptions.Builder(mapView.context)
                .accessToken(mapView.getMapboxMap().getResourceOptions().accessToken)
                .build()
        )
    }
    val routeLineOptions =
        remember(mapView) { MapboxRouteLineOptions.Builder(mapView.context).build() }
    val routeLineApi = remember(mapView) { MapboxRouteLineApi(routeLineOptions) }
    val routeLineView = remember(mapView) { MapboxRouteLineView(routeLineOptions) }

    LaunchedEffect(key1 = state.feedingRouteState) {
        when (state.feedingRouteState.isRouteActive) {
            true -> {
                state.currentFeedingPoint?.location?.let { feedingPointLocation ->
                    mapView.drawRoute(
                        mapboxNavigation,
                        routeLineApi,
                        routeLineView,
                        state.currentLocation.toPoint(),
                        feedingPointLocation.toPoint(),
                        onRouteResult = onRouteResult
                    )
                }
                if (mapView.getMapboxMap().getStyle()?.isStyleLoaded == true) {
                    setLocationOnRoute(mapView, state)
                }
            }
            false -> {
                mapView.removeRoute(routeLineApi, routeLineView)
                mapView.setLocation(location = state.currentLocation)
            }
        }
    }

    // App crashes if we don't destroy mapNavigation onDetach
    mapView.doOnDetach {
        mapboxNavigation.onDestroy()
    }
}

private fun setLocationOnRoute(mapView: MapView, state: HomeState) {
    state.currentFeedingPoint?.location?.let { feedingPointLocation ->
        mapView.setLocation(
            points = listOf(
                state.currentLocation.toPoint(),
                feedingPointLocation.toPoint()
            )
        )
    }
}
