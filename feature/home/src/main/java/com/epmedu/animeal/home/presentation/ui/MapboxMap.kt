package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.ui.map.MapBoxInitOptions
import com.epmedu.animeal.home.presentation.ui.map.MapUiSettings
import com.epmedu.animeal.home.presentation.ui.map.MarkerController
import com.epmedu.animeal.home.presentation.ui.map.rememberMapInitOptions
import com.epmedu.animeal.home.presentation.ui.map.rememberMapUiSettings
import com.epmedu.animeal.home.presentation.ui.map.rememberMapViewWithLifecycle
import com.epmedu.animeal.home.presentation.ui.map.setGesturesListener
import com.epmedu.animeal.home.presentation.ui.map.setLocation
import com.epmedu.animeal.home.presentation.viewmodel.HomeState

@Composable
fun MapboxMap(
    state: HomeState,
    onFeedingPointClick: (point: FeedingPointUi) -> Unit,
    onMapInteraction: () -> Unit
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

    mapView.setGesturesListener(onMapInteraction = onMapInteraction)

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