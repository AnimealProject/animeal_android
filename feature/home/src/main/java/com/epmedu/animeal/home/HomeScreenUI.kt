package com.epmedu.animeal.home

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.switch.AnimealSwitch
import com.epmedu.animeal.resources.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.ResourceOptions
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar

@Composable
internal fun HomeScreenUI(state: HomeState) {
    CheckLocationPermission {
        Box(modifier = Modifier.fillMaxSize()) {
            MapboxMap(state)
            AnimealSwitch(
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .padding(top = 24.dp),
                onSelectTab = {}
            )
        }
    }
}

@Composable
private fun MapboxMap(state: HomeState) {
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckLocationPermission(onGranted: @Composable () -> Unit) {
    var isPermissionRequested by remember {
        mutableStateOf(false)
    }
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = {
            isPermissionRequested = true
        }
    )

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }
    with(permissionState.status) {
        when {
            isGranted -> onGranted()
            shouldShowRationale -> RationaleText()
            isPermissionRequested && !isGranted && !shouldShowRationale -> NavigateToSettingsPrompt()
        }
    }
}

@Composable
private fun RationaleText() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.location_rationale_text),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun NavigateToSettingsPrompt() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.location_permission_denied),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
        AnimealButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp),
            text = stringResource(R.string.navigate_to_app_settings),
            onClick = { context.launchAppSettings() }
        )
    }
}
