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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.epmedu.animeal.extensions.formatMetersToKilometers
import com.epmedu.animeal.extensions.formatNumberToHourMin
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState
import com.epmedu.animeal.foundation.tabs.AnimealSwitch
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.ui.map.GesturesListener
import com.epmedu.animeal.home.presentation.ui.map.MapBoxInitOptions
import com.epmedu.animeal.home.presentation.ui.map.MapUiSettings
import com.epmedu.animeal.home.presentation.ui.map.MarkerController
import com.epmedu.animeal.home.presentation.ui.map.RouteTopBar
import com.epmedu.animeal.home.presentation.ui.map.RouteView
import com.epmedu.animeal.home.presentation.ui.map.rememberMapInitOptions
import com.epmedu.animeal.home.presentation.ui.map.rememberMapUiSettings
import com.epmedu.animeal.home.presentation.ui.map.rememberMapViewWithLifecycle
import com.epmedu.animeal.home.presentation.ui.map.setLocation
import com.epmedu.animeal.home.presentation.ui.map.setLocationOnRoute
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.router.model.RouteResult
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.delegates.listeners.OnStyleLoadedListener

@Composable
@Suppress("LongParameterList")
internal fun HomeMapbox(
    state: HomeState,
    onFeedingPointSelect: (point: FeedingPointModel) -> Unit,
    onGeolocationClick: (MapView) -> Unit,
    onInitialLocationDisplay: () -> Unit,
    onRouteResult: (result: RouteResult) -> Unit,
    onCancelRouteClick: () -> Unit,
    onMapInteraction: () -> Unit,
    onSelectTab: (AnimalType) -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        val mapboxMapView = rememberMapboxMapView(homeState = state)

        /** Show user location after successful feeding operation */
        if (state.feedingPointState.feedingConfirmationState == FeedingConfirmationState.Showing) {
            ShowUserCurrentLocation(state, mapboxMapView)
        }

        MapboxMap(
            mapboxMapView = mapboxMapView,
            state = state,
            onFeedingPointClick = onFeedingPointSelect,
            onInitialLocationDisplay = onInitialLocationDisplay,
            onRouteResult = onRouteResult,
            onMapInteraction = onMapInteraction,
        )

        when (state.feedingPointState.feedingRouteState) {
            is FeedingRouteState.Disabled -> {
                AnimealSwitch(
                    modifier = Modifier
                        .statusBarsPadding()
                        .align(alignment = Alignment.TopCenter)
                        .padding(top = 24.dp),
                    onSelectTab = onSelectTab,
                    defaultAnimalType = state.feedingPointState.defaultAnimalType
                )
            }
            is FeedingRouteState.Active -> {
                RouteTopBar(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(top = 16.dp)
                        .padding(horizontal = 20.dp),
                    timeLeft = context.formatNumberToHourMin(
                        (state.feedingPointState.feedingRouteState as FeedingRouteState.Active).timeLeft
                    )
                        ?: stringResource(R.string.calculating_route),
                    distanceLeft = (state.feedingPointState.feedingRouteState as FeedingRouteState.Active).distanceLeft?.run {
                        " • ${context.formatMetersToKilometers(this)}"
                    } ?: "",
                    onCancelClick = onCancelRouteClick
                )
            }
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
private fun ShowUserCurrentLocation(
    state: HomeState,
    mapboxMapView: MapView
) {
    LaunchedEffect(key1 = state.geolocationPermissionStatus, key2 = state.gpsSettingState) {
        if (state.geolocationPermissionStatus == PermissionStatus.Granted &&
            state.gpsSettingState == GpsSettingState.Enabled
        ) mapboxMapView.showCurrentLocation(state.locationState.location)
    }
}

@Composable
private fun MapboxMap(
    mapboxMapView: MapView,
    state: HomeState,
    onFeedingPointClick: (point: FeedingPointModel) -> Unit,
    onInitialLocationDisplay: () -> Unit,
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
            when (state.feedingPointState.feedingRouteState) {
                is FeedingRouteState.Disabled -> mapboxMapView.setLocation(state.locationState.location)
                is FeedingRouteState.Active -> setLocationOnRoute(mapboxMapView, state)
            }
        }
    }

    LaunchedEffect(key1 = state.feedingPointState.feedingPoints) {
        markerController.drawMarkers(
            feedingPoints = state.feedingPointState.feedingPoints
        )
    }

    LaunchedEffect(key1 = state.feedingPointState.currentFeedingPoint) {
        if (state.feedingPointState.feedingRouteState is FeedingRouteState.Active) {
            markerController.drawSelectedMarkerBackground(null)
        } else {
            markerController.drawSelectedMarkerBackground(state.feedingPointState.currentFeedingPoint)
        }
    }

    LaunchedEffect(key1 = state.locationState) {
        when {
            state.locationState.isUndefined -> mapboxMapView.setLocation(state.locationState.location)
            state.locationState.isInitial -> {
                mapboxMapView.setLocation(state.locationState.location)
                onInitialLocationDisplay()
            }
        }
    }

    // TODO check if it is needed to remove, probably yes
    mapboxMapView.getMapboxMap().addOnStyleLoadedListener(onStyleLoadedListener)
    mapboxMapView.GesturesListener(onMapInteraction = onMapInteraction)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { mapboxMapView }
    )

    RouteView(mapView = mapboxMapView, state = state, onRouteResult = onRouteResult)
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

internal fun MapView.showCurrentLocation(location: MapLocation) = getMapboxMap().setCamera(
    CameraOptions.Builder()
        .center(Point.fromLngLat(location.longitude, location.latitude))
        .build()
)
