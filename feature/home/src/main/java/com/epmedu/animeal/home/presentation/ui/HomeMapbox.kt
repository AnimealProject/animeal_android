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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimealSwitch
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.home.presentation.ui.map.MapBoxInitOptions
import com.epmedu.animeal.home.presentation.ui.map.MapUiSettings
import com.epmedu.animeal.home.presentation.ui.map.MarkerController
import com.epmedu.animeal.home.presentation.ui.map.rememberMapInitOptions
import com.epmedu.animeal.home.presentation.ui.map.rememberMapUiSettings
import com.epmedu.animeal.home.presentation.ui.map.rememberMapViewWithLifecycle
import com.epmedu.animeal.home.presentation.ui.map.setGesturesListener
import com.epmedu.animeal.home.presentation.ui.map.setLocation
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView

@Composable
internal fun HomeMapbox(
    state: HomeState,
    onFeedingPointSelect: (point: FeedingPointModel) -> Unit,
    onGeolocationClick: (MapView) -> Unit,
    onMapInteraction: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val mapboxMapView = rememberMapboxMapView(homeState = state)

        MapboxMap(
            mapboxMapView = mapboxMapView,
            state = state,
            onFeedingPointClick = onFeedingPointSelect,
            onMapInteraction = onMapInteraction,
        )

        AnimealSwitch(
            modifier = Modifier
                .statusBarsPadding()
                .align(alignment = Alignment.TopCenter)
                .padding(top = 24.dp),
            onSelectTab = {}
        )

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
    onMapInteraction: () -> Unit,
) {
    val markerController = remember(mapboxMapView) {
        MarkerController(
            mapView = mapboxMapView,
            onFeedingPointClick = onFeedingPointClick
        )
    }

    mapboxMapView.setGesturesListener(onMapInteraction = onMapInteraction)

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

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { mapboxMapView }
    )
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
