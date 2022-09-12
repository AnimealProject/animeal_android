package com.epmedu.animeal.home.presentation

import android.Manifest
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.epmedu.animeal.common.data.model.MapLocation
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.switch.AnimealSwitch
import com.epmedu.animeal.home.presentation.ui.FeedSpotSheetContent
import com.epmedu.animeal.home.presentation.ui.HomeBottomSheetLayout
import com.epmedu.animeal.home.presentation.ui.HomeBottomSheetState
import com.epmedu.animeal.home.presentation.ui.HomeBottomSheetValue
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
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

@Suppress("LongMethod")
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreenUI(
    state: HomeState,
    bottomSheetState: HomeBottomSheetState,
    onChangeBottomNavBarVisibility: (Boolean) -> Unit,
    onEvent: (HomeScreenEvent) -> Unit
) {
    LaunchedEffect(bottomSheetState.progress) {
        onChangeBottomNavBarVisibility(
            if (bottomSheetState.progress.to != HomeBottomSheetValue.Hidden) false
            else bottomSheetState.isHidden
        )
    }

    val contentAlpha: Float by animateFloatAsState(
        targetValue = when {
            bottomSheetState.isExpanding -> bottomSheetState.progress.fraction
            bottomSheetState.isCollapsing -> 1f - bottomSheetState.progress.fraction
            else -> 0f
        }
    )

    val buttonAlpha: Float by animateFloatAsState(
        targetValue = when {
            bottomSheetState.isShowing -> bottomSheetState.progress.fraction
            bottomSheetState.isHiding -> 1f - bottomSheetState.progress.fraction
            else -> 1f
        }
    )

    CheckLocationPermission {
        HomeBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            sheetContent = {
                FeedSpotSheetContent(
                    feedSpot = state.currentFeedSpot,
                    contentAlpha = contentAlpha,
                    onFavouriteChange = {
                        onEvent(HomeScreenEvent.FeedSpotFavouriteChange(isFavourite = it))
                    }
                )
            },
            sheetControls = {
                FeedSpotActionButton(
                    alpha = buttonAlpha,
                    onClick = {}
                )
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MapboxMap(
                    mapBoxPublicKey = state.mapBoxPublicKey,
                    mapBoxStyleUri = state.mapBoxStyleUri,
                    location = state.currentLocation
                )
                AnimealSwitch(
                    modifier = Modifier
                        .statusBarsPadding()
                        .align(alignment = Alignment.TopCenter)
                        .padding(top = 24.dp),
                    onSelectTab = {}
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            onEvent(HomeScreenEvent.FeedSpotSelected())
                        },
                    text = "Feed spot"
                )
            }
        }
    }
}

@Composable
private fun MapboxMap(
    mapBoxPublicKey: String,
    mapBoxStyleUri: String,
    location: MapLocation,
) {
    val mapBoxView = MapView(mapBoxPublicKey, mapBoxStyleUri)

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

        if (!initialLocationReceived && !location.isInitial) {
            initialLocationReceived = true
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .zoom(17.0)
                    .center(
                        Point.fromLngLat(
                            location.longitude,
                            location.latitude
                        )
                    ).build()
            )
        }
    }
}

@Composable
private fun MapView(mapboxPublicKey: String, mapBoxStyleUri: String): MapView {
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
private fun FeedSpotActionButton(
    alpha: Float,
    onClick: () -> Unit
) {
    AnimealButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .alpha(alpha),
        text = stringResource(R.string.i_will_feed),
        onClick = onClick,
    )
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