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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.switch.AnimealSwitch
import com.epmedu.animeal.foundation.theme.CustomColor
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
                    .statusBarsPadding()
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
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreen(
    changeBottomBarVisibility: (Boolean) -> Unit,
) {
    CheckLocationPermission {
        Maps(
            changeBottomBarVisibility = changeBottomBarVisibility,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Maps(
    changeBottomBarVisibility: (Boolean) -> Unit,
) {
    val bottomSheetState = rememberHomeBottomSheetState(HomeBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    LaunchedEffect(bottomSheetState.progress) {
        changeBottomBarVisibility(
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

    HomeBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            BottomSheetContent(contentAlpha)
        },
        sheetControls = {
            BottomSheetButton(buttonAlpha)
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AnimealSwitch(
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .padding(top = 24.dp),
                onTabSelected = {}
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {
                        scope.launch {
                            if (bottomSheetState.isHidden) {
                                bottomSheetState.show()
                            } else {
                                bottomSheetState.hide()
                            }
                        }
                    },
                text = stringResource(R.string.home),
            )
        }
    }
}

@Composable
fun BottomSheetButton(
    alpha: Float,
) {
    AnimealButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .alpha(alpha),
        text = stringResource(R.string.i_will_feed),
        onClick = {},
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    contentAlpha: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.surface)
            .padding(
                top = 8.dp,
                bottom = 110.dp,
                start = 24.dp,
                end = 24.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BottomSheetKnob()
        BottomSheetHeader()
        BottomSheetContentDetails(contentAlpha)
    }
}

@Composable
fun BottomSheetKnob() {
    Divider(
        modifier = Modifier
            .width(18.dp)
            .clip(RoundedCornerShape(2.dp)),
        thickness = 4.dp,
    )
}

@Composable
fun BottomSheetHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.secondaryVariant)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Near to Bukia Garden M.S Technical University",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onSurface,
                maxLines = 2,
            )
            Text(
                text = "There is no food",
                style = MaterialTheme.typography.caption,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.error,
                maxLines = 1,
            )
        }
        HeartButton(
            modifier = Modifier.align(Alignment.Top),
            selected = false,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun BottomSheetContentDetails(
    scrimAlpha: Float,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colors.onSurface,
            LocalContentAlpha provides scrimAlpha,
        ) {
            Text(
                text = "This area covers about 100 sq.m. -S, it starts with Bukia Garden and Sports At the palace. There are about 1000 homeless people here The dog lives with the habit of helping You need.",
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = "Last feeder",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
            )

            BottomSheetLastFeeder()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HeartButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
) {
    val iconColor =
        if (selected) MaterialTheme.colors.error
        else MaterialTheme.colors.secondaryVariant

    Surface(
        modifier = modifier.size(32.dp),
        shape = CircleShape,
        elevation = 1.dp,
        onClick = {},
    ) {
        Icon(
            modifier = Modifier.requiredSize(16.dp),
            imageVector = Icons.Filled.Favorite,
            tint = iconColor,
            contentDescription = null
        )
    }
}

@Composable
fun BottomSheetLastFeeder() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(CustomColor.LynxWhite),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                tint = CustomColor.Orange,
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Giorgi Abutidze",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
            )
            Text(
                text = "14 hours ago",
                style = MaterialTheme.typography.caption,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                maxLines = 1,
            )
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