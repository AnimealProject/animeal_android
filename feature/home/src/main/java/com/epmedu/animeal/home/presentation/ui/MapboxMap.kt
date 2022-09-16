package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.ResourceOptions
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar

@Composable
fun MapboxMap(state: HomeState) {
    val mapBoxView = mapView(state.mapBoxPublicKey, state.mapBoxStyleUri)

    var initialLocationReceived by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) { initialLocationReceived = false }

    AndroidView(
        factory = { mapBoxView },
        modifier = Modifier.fillMaxSize()
    ) { mapView ->
        mapView.scalebar.enabled = false
        mapView.location.updateSettings {
            enabled = true
            pulsingEnabled = true
        }

        if (!initialLocationReceived && !state.currentLocation.isInitial) {
            initialLocationReceived = true
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .zoom(17.0)
                    .center(
                        Point.fromLngLat(
                            state.currentLocation.longitude,
                            state.currentLocation.latitude
                        )
                    ).build()
            )
        }
    }
}

@Composable
private fun mapView(mapboxPublicKey: String, mapBoxStyleUri: String): MapView {
    val context = LocalContext.current
    val mapInitOptions = MapInitOptions(
        context = context,
        resourceOptions = ResourceOptions.Builder().accessToken(mapboxPublicKey).build(),
        styleUri = mapBoxStyleUri
    )

    val mapView = remember(mapInitOptions) {
        MapView(context, mapInitOptions)
    }

    return mapView
}
