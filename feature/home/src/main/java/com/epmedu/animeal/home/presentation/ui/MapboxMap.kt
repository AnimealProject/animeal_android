package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.ui.map.*
import com.epmedu.animeal.home.presentation.viewmodel.HomeState

@Composable
fun MapboxMap(
    state: HomeState,
    onFeedingPointClick: (point: FeedingPointUi) -> Unit
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
                userLocationOnMap = true,
                compassEnabled = false
            )
        )
    )

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

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { mapView }
    )
}