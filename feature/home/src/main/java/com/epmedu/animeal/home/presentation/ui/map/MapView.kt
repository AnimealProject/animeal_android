package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.ResourceOptions
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar

@Composable
fun rememberMapViewWithLifecycle(
    mapBoxInitOptions: MapBoxInitOptions,
    uiSettings: MapUiSettings
): MapView {
    val context = LocalContext.current

    val mapInitOptions = MapInitOptions(
        context = context,
        resourceOptions = ResourceOptions.Builder()
            .accessToken(mapBoxInitOptions.publicKey)
            .build(),
        styleUri = mapBoxInitOptions.styleUrl
    )

    val mapView = remember(mapBoxInitOptions, uiSettings) {
        MapView(context, mapInitOptions).apply {
            scalebar.enabled = uiSettings.scalebar

            location.updateSettings {
                enabled = uiSettings.userLocationOnMap
                pulsingEnabled = uiSettings.locationPulsing
            }
        }
    }

    // TODO: Use Activity lifecycle to avoid memory leaks
    DisposableEffect(mapView) {
        onDispose {
            mapView.onDestroy()
        }
    }

    return mapView
}

fun MapView.setLocation(location: MapLocation) = getMapboxMap().setCamera(
    CameraOptions.Builder()
        .zoom(13.0)
        .center(Point.fromLngLat(location.longitude, location.latitude))
        .build()
)