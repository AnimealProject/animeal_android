package com.epmedu.animeal.home.presentation

import android.os.CountDownTimer
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.HOUR_IN_MILLIS
import com.epmedu.animeal.extensions.MINUTE_IN_MILLIS
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.extensions.launchGpsSettings
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointActionButton
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.HomeScreenEvent.*
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.model.WillFeedState
import com.epmedu.animeal.home.presentation.ui.*
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeScreenUI(
    state: HomeState,
    bottomSheetState: AnimealBottomSheetState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
) {
    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()

    val scope = rememberCoroutineScope()
    val timerHandler: CountDownTimer = remember { getTimeHandler(onScreenEvent) }

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
                    feedingPoint = FeedingPointModel(feedingPoint),
                    contentAlpha = contentAlpha,
                    modifier = Modifier.wrapContentHeight(),
                    onFavouriteChange = {
                        onScreenEvent(FeedingPointFavouriteChange(isFavourite = it))
                    }
                )
            }
        },
        sheetControls = {
            FeedingPointActionButton(
                alpha = buttonAlpha,
                enabled = state.currentFeedingPoint?.animalStatus == AnimalState.RED,
                onClick = { onScreenEvent(WillFeedEvent.ShowWillFeedDialog) }
            )
        }
    ) {
        HomeGeolocationPermission(
            homeState = state,
            onScreenEvent = onScreenEvent,
        ) { geolocationPermissionState ->
            HomeMapbox(
                state = state,
                onFeedingPointSelect = { onScreenEvent(HomeScreenEvent.FeedingPointSelected(it.id)) },
                onMapInteraction = {
                    if (bottomSheetState.isExpanding && !state.feedingRouteState.isRouteActive) {
                        scope.launch { bottomSheetState.show() }
                    }
                },
                onCancelRouteClick = {
                    onScreenEvent(RouteEvent.FeedingRouteCancellationRequest)
                    timerHandler.cancel()
                },
                onRouteResult = { result ->
                    onScreenEvent(RouteEvent.FeedingRouteUpdateRequest(result))
                },
                onGeolocationClick = { mapView ->
                    when (state.geolocationPermissionStatus) {
                        PermissionStatus.Restricted -> mapView.context.launchAppSettings()
                        PermissionStatus.Denied -> geolocationPermissionState.launchPermissionRequest()
                        PermissionStatus.Granted -> when (state.gpsSettingState) {
                            GpsSettingState.Disabled -> mapView.context.launchGpsSettings()
                            GpsSettingState.Enabled -> mapView.showCurrentLocation(state.locationState.location)
                        }
                    }
                }
            )
        }

        WillFeedConfirmationDialog(state, onScreenEvent) {
            timerHandler.start()
        }
    }
}

@Composable
private fun WillFeedConfirmationDialog(
    state: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    onAgreeClick: () -> Unit
) {
    if (state.willFeedState is WillFeedState.Showing) {
        FeedConfirmationDialog(
            onAgreeClick = {
                onScreenEvent(WillFeedEvent.DismissWillFeedDialog)
                onScreenEvent(RouteEvent.FeedingRouteStartRequest)
                onAgreeClick.invoke()
            },
            onCancelClick = { onScreenEvent(WillFeedEvent.DismissWillFeedDialog) }
        )
    }
}

@Composable
fun OnBackHandling(
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

private fun getTimeHandler(onScreenEvent: (HomeScreenEvent) -> Unit) =
    object : CountDownTimer(HOUR_IN_MILLIS, MINUTE_IN_MILLIS) {
        override fun onTick(timeLeftInMillis: Long) {
            onScreenEvent(RouteEvent.FeedingTimerUpdateRequest(timeLeftInMillis))
        }

        override fun onFinish() {
            cancel()
        }
    }

