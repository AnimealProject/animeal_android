package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.doOnDetach
import com.epmedu.animeal.extensions.formatNumberToHourMin
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.model.MapLocation.Companion.toPoint
import com.epmedu.animeal.foundation.switch.AnimealSwitch
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.model.MapPath
import com.epmedu.animeal.home.presentation.model.RouteResult
import com.epmedu.animeal.home.presentation.ui.map.*
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.delegates.listeners.OnStyleLoadedListener
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions

@Composable
internal fun HomeMapbox(
    state: HomeState,
    onFeedingPointSelect: (point: FeedingPointModel) -> Unit,
    onGeolocationClick: (MapView) -> Unit,
    onRouteResult: (result: RouteResult) -> Unit,
    onCancelRouteClick: () -> Unit,
    onMapInteraction: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val mapboxMapView = rememberMapboxMapView(homeState = state)

        MapboxMap(
            mapboxMapView = mapboxMapView,
            state = state,
            onFeedingPointClick = onFeedingPointSelect,
            onRouteResult = onRouteResult,
            onMapInteraction = onMapInteraction,
        )

        if (!state.feedingRouteState.isRouteActive) {
            AnimealSwitch(
                modifier = Modifier
                    .statusBarsPadding()
                    .align(alignment = Alignment.TopCenter)
                    .padding(top = 24.dp),
                onSelectTab = {}
            )
        } else {
            RouteTopBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 16.dp)
                    .padding(horizontal = 20.dp),
                timeLeft = state.feedingRouteState.timeLeft?.formatNumberToHourMin()
                    ?: stringResource(R.string.calculating_route),
                distanceLeft = state.feedingRouteState.distanceLeft?.run { " • $this" } ?: "",
                onCancelClick = onCancelRouteClick
            )
        }

        GeoLocationFloatingActionButton(
            modifier = Modifier
                .bottomBarPadding()
                .padding(
                    bottom = 24.dp,
                    end = 24.dp
                )
                .align(alignment = Alignment.BottomEnd),
            onClick = { onGeolocationClick(mapboxMapView) }
        )
    }
}

@Composable
private fun MapboxMap(
    mapboxMapView: MapView,
    state: HomeState,
    onFeedingPointClick: (point: FeedingPointModel) -> Unit,
    onRouteResult: (result: RouteResult) -> Unit,
    onMapInteraction: () -> Unit,
) {
    val markerController = remember(mapboxMapView) {
        MarkerController(
            mapView = mapboxMapView,
            onFeedingPointClick = onFeedingPointClick
        )
    }

    // If we return from other tab and there was a route active in map the camera zoom will not work
    // so we have to make sure the map is loaded before setting location
    val onStyleLoadedListener = OnStyleLoadedListener { event ->
        event.end?.run {
            if (state.feedingRouteState.isRouteActive) {
                setLocationOnRoute(mapboxMapView, state)
            } else {
                mapboxMapView.setLocation(state.currentLocation)
            }
        }
    }

    LaunchedEffect(key1 = state.feedingPoints) {
        markerController.drawMarkers(
            feedingPoints = state.feedingPoints
        )
    }

    LaunchedEffect(key1 = state.locationState) {
        if (state.locationState.isInitial || state.locationState.isUndefined) {
            mapboxMapView.setLocation(state.locationState.location)
        }
    }

    // TODO check if it is needed to remove, probably yes
    mapboxMapView.getMapboxMap().addOnStyleLoadedListener(onStyleLoadedListener)
    mapboxMapView.setGesturesListener(onMapInteraction = onMapInteraction)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { mapboxMapView }
    )

    SetUpRoute(mapView = mapboxMapView, state = state, onRouteResult = onRouteResult)

}

@Composable
private fun rememberMapboxMapView(homeState: HomeState): MapView {
    return rememberMapViewWithLifecycle(
        mapBoxInitOptions = rememberMapInitOptions(
            MapBoxInitOptions(
                publicKey = homeState.mapBoxPublicKey,
                styleUrl = homeState.mapBoxStyleUri
            )
        ),
        uiSettings = rememberMapUiSettings(
            MapUiSettings(
                scalebar = false,
                userLocationOnMap = true,
                compassEnabled = false
            )
        )
    )
}

@Composable
private fun SetUpRoute(
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
            FeedingRouteState.Started -> {
                state.currentFeedingPoint?.location?.let { feedingPointLocation ->
                    mapView.fetchRoute(
                        mapBoxRouteInitOptions,
                        mapboxNavigation,
                        MapPath(
                            state.currentLocation.toPoint(),
                            feedingPointLocation.toPoint()
                        ),
                        onRouteResult = onRouteResult
                    )
                }
            }
            FeedingRouteState.Disabled -> {
                mapView.removeRoute(mapBoxRouteInitOptions)
            }
            is FeedingRouteState.Updated -> {
                state.feedingRouteState.routeData?.let {
                    mapView.drawRoute(mapBoxRouteInitOptions, it)
                }
                if (mapView.getMapboxMap().getStyle()?.isStyleLoaded == true) {
                    setLocationOnRoute(mapView, state)
                }
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

internal fun MapView.showCurrentLocation(location: MapLocation) = getMapboxMap().setCamera(
    CameraOptions.Builder()
        .center(Point.fromLngLat(location.longitude, location.latitude))
        .build()
)
