package com.epmedu.animeal.home.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.extensions.launchGpsSettings
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointActionButton
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.HomeScreenEvent.ErrorShowed
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent.FavouriteChange
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.TimerEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.WillFeedEvent
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.model.WillFeedState
import com.epmedu.animeal.home.presentation.ui.FeedingExpiredDialog
import com.epmedu.animeal.home.presentation.ui.HomeGeolocationPermission
import com.epmedu.animeal.home.presentation.ui.HomeMapbox
import com.epmedu.animeal.home.presentation.ui.showCurrentLocation
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.timer.data.model.TimerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.mapbox.maps.MapView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Suppress("LongMethod")
internal fun HomeScreenUI(
    state: HomeState,
    bottomSheetState: AnimealBottomSheetState,
    onScreenEvent: (HomeScreenEvent) -> Unit
) {
    val context = LocalContext.current
    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()
    val scope = rememberCoroutineScope()

    if (state.isError) {
        Toast.makeText(
            context,
            context.getString(R.string.something_went_wrong),
            Toast.LENGTH_SHORT
        ).show()
        onScreenEvent(ErrorShowed)
    }

    val hideBottomSheet: () -> Unit = {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(key1 = state.timerState, key2 = state.feedingRouteState) {
        when (state.timerState) {
            is TimerState.Active -> {
                onScreenEvent(
                    RouteEvent.FeedingTimerUpdateRequest(
                        state.timerState.timeLeft
                    )
                )
            }
            TimerState.Expired -> {
                hideBottomSheet()
                onScreenEvent(TimerEvent.Expired)
            }
            else -> Unit
        }
    }

    if (state.timerState == TimerState.Expired) {
        FeedingExpiredDialog(
            onConfirm = {
                onScreenEvent(TimerEvent.ExpirationAccepted)
            }
        )
    }

    LaunchedEffect(state.currentFeedingPoint) {
        if (state.currentFeedingPoint == null) bottomSheetState.hide()
    }

    OnBackHandling(
        scope = scope,
        bottomSheetState = bottomSheetState,
        state = state,
        onScreenEvent = onScreenEvent
    )

    AnimealBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            state.currentFeedingPoint?.let { feedingPoint ->
                FeedingPointSheetContent(
                    feedingPoint = feedingPoint,
                    contentAlpha = contentAlpha,
                    modifier = Modifier.wrapContentHeight(),
                    onFavouriteChange = { onScreenEvent(FavouriteChange(isFavourite = it)) }
                )
            }
        },
        sheetControls = {
            state.currentFeedingPoint?.let { feedingPoint ->
                FeedingPointActionButton(
                    alpha = buttonAlpha,
                    enabled = feedingPoint.feedStatus == FeedStatus.RED,
                    onClick = { onScreenEvent(WillFeedEvent.ShowWillFeedDialog) }
                )
            }
        }
    ) {
        HomeGeolocationPermission(
            homeState = state,
            onScreenEvent = onScreenEvent,
        ) { geolocationPermissionState ->
            HomeMapbox(
                state = state,
                onFeedingPointSelect = { onScreenEvent(FeedingPointEvent.Select(it)) },
                onMapInteraction = {
                    if (bottomSheetState.isExpanding && !state.feedingRouteState.isRouteActive) {
                        scope.launch { bottomSheetState.show() }
                    }
                },
                onCancelRouteClick = {
                    onScreenEvent(FeedingEvent.Cancel)
                },
                onRouteResult = { result ->
                    onScreenEvent(RouteEvent.FeedingRouteUpdateRequest(result))
                },
                onGeolocationClick = { mapView ->
                    onGeoLocationClick(mapView, state, geolocationPermissionState)
                }
            )
        }
    }

    WillFeedConfirmationDialog(scope, bottomSheetState, state, onScreenEvent, hideBottomSheet)
}

@Composable
private fun WillFeedConfirmationDialog(
    scope: CoroutineScope,
    bottomSheetState: AnimealBottomSheetState,
    state: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    onHideBottomSheet: () -> Unit
) {
    if (state.willFeedState is WillFeedState.Showing) {
        FeedConfirmationDialog(
            onAgreeClick = {
                onScreenEvent(WillFeedEvent.DismissWillFeedDialog)
                scope.launch { bottomSheetState.hide() }
                onScreenEvent(FeedingEvent.Start)
                onHideBottomSheet()
            },
            onCancelClick = { onScreenEvent(WillFeedEvent.DismissWillFeedDialog) }
        )
    }
}

@Composable
private fun OnBackHandling(
    scope: CoroutineScope,
    bottomSheetState: AnimealBottomSheetState,
    state: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit
) {
    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    BackHandler(enabled = state.willFeedState is WillFeedState.Showing) {
        scope.launch { onScreenEvent(WillFeedEvent.DismissWillFeedDialog) }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun onGeoLocationClick(
    mapView: MapView,
    state: HomeState,
    geolocationPermission: PermissionState
) {
    when (state.geolocationPermissionStatus) {
        PermissionStatus.Restricted -> mapView.context.launchAppSettings()
        PermissionStatus.Denied -> geolocationPermission.launchPermissionRequest()
        PermissionStatus.Granted -> when (state.gpsSettingState) {
            GpsSettingState.Disabled -> mapView.context.launchGpsSettings()
            GpsSettingState.Enabled -> mapView.showCurrentLocation(state.locationState.location)
        }
    }
}
