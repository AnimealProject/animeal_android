package com.epmedu.animeal.home.presentation

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.extensions.requestGpsByDialog
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.ui.DeletePhotoDialog
import com.epmedu.animeal.feeding.presentation.ui.FeedingConfirmationDialog
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointActionButton
import com.epmedu.animeal.feeding.presentation.ui.MarkFeedingDoneActionButton
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.HomeScreenEvent.ErrorShowed
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingGalleryEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.TimerCancellationEvent
import com.epmedu.animeal.home.presentation.model.CameraState
import com.epmedu.animeal.home.presentation.model.CancellationRequestState
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.ui.CameraPermissionRequestDialog
import com.epmedu.animeal.home.presentation.ui.FeedingCancellationRequestDialog
import com.epmedu.animeal.home.presentation.ui.FeedingExpiredDialog
import com.epmedu.animeal.home.presentation.ui.FeedingSheet
import com.epmedu.animeal.home.presentation.ui.HomeMapbox
import com.epmedu.animeal.home.presentation.ui.HomePermissions
import com.epmedu.animeal.home.presentation.ui.showCurrentLocation
import com.epmedu.animeal.home.presentation.ui.thankyou.ThankYouDialog
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.epmedu.animeal.router.presentation.RouteEvent
import com.epmedu.animeal.timer.data.model.TimerState
import com.epmedu.animeal.timer.presentation.handler.TimerEvent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.mapbox.maps.MapView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Suppress("LongMethod", "LongParameterList")
internal fun HomeScreenUI(
    state: HomeState,
    bottomSheetState: AnimealBottomSheetState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    onRouteEvent: (RouteEvent) -> Unit,
    onFeedingEvent: (FeedingEvent) -> Unit,
    onFeedingPointEvent: (FeedingPointEvent) -> Unit,
    onTimerEvent: (TimerEvent) -> Unit,
) {
    val context = LocalContext.current
    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()
    val scope = rememberCoroutineScope()
    val isFeedingDialogShowing = rememberSaveable { mutableStateOf(false) }
    val isCameraPermissionDialogShowing = rememberSaveable { mutableStateOf(false) }

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

    LaunchedEffect(key1 = state.timerState) {
        when (state.timerState) {
            is TimerState.Active -> {
                onRouteEvent(
                    RouteEvent.FeedingTimerUpdateRequest(
                        state.timerState.timeLeft
                    )
                )
            }
            TimerState.Expired -> {
                hideBottomSheet()
                onFeedingEvent(FeedingEvent.Expired)
            }
            else -> Unit
        }
    }

    OnState(state, onScreenEvent, onTimerEvent)

    LaunchedEffect(state.feedingPointState.currentFeedingPoint) {
        if (state.feedingPointState.currentFeedingPoint == null) bottomSheetState.hide()
    }

    LaunchedEffect(key1 = bottomSheetState.isHidden, key2 = state.feedingRouteState) {
        if (bottomSheetState.isHidden && state.feedingRouteState is FeedingRouteState.Disabled) {
            onFeedingPointEvent(FeedingPointEvent.Deselect)
        }
    }

    OnBackHandling(scope = scope, bottomSheetState = bottomSheetState)

    AnimealBottomSheetLayout(
        skipHalfExpanded = state.feedingRouteState is FeedingRouteState.Active,
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            state.feedingPointState.currentFeedingPoint?.let { feedingPoint ->
                FeedingSheet(
                    feedingState = state.feedingRouteState,
                    cameraState = state.cameraState,
                    feedingPoint = feedingPoint,
                    feedingPhotos = state.feedingPhotos,
                    contentAlpha = contentAlpha,
                    onFavouriteChange = {
                        onFeedingPointEvent(
                            FeedingPointEvent.FavouriteChange(
                                isFavourite = it
                            )
                        )
                    },
                    onTakePhotoClick = { openCamera(state, onScreenEvent, context) },
                    onDeletePhotoClick = { onScreenEvent(FeedingGalleryEvent.DeletePhoto(it)) },
                    onShowOnMap = {}
                )
            }
        },
        sheetControls = {
            state.feedingPointState.currentFeedingPoint?.let { feedingPoint ->
                when (state.feedingRouteState) {
                    is FeedingRouteState.Active -> {
                        if (state.feedingPointState.feedingConfirmationState == FeedingConfirmationState.Loading) {
                            CircularProgressIndicator(Modifier.padding(24.dp))
                        } else {
                            MarkFeedingDoneActionButton(
                                alpha = buttonAlpha,
                                enabled = state.feedingPhotos.isNotEmpty() && state.cameraState == CameraState.Disabled,
                                onClick = { onFeedingEvent(FeedingEvent.Finish(state.feedingPhotos)) }
                            )
                        }
                    }
                    else -> {
                        FeedingPointActionButton(
                            alpha = buttonAlpha,
                            enabled = feedingPoint.feedStatus == FeedStatus.RED,
                            onClick = {
                                when (state.cameraPermissionStatus) {
                                    PermissionStatus.Granted -> isFeedingDialogShowing.value = true
                                    else -> isCameraPermissionDialogShowing.value = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        HomePermissions(
            homeState = state,
            onScreenEvent = onScreenEvent,
        ) { geolocationPermissionState ->
            HomeMapbox(
                state = state,
                onFeedingPointSelect = { onFeedingPointEvent(FeedingPointEvent.Select(it)) },
                onMapInteraction = { onScreenEvent(HomeScreenEvent.MapInteracted) },
                onInitialLocationDisplay = { onScreenEvent(HomeScreenEvent.InitialLocationWasDisplayed) },
                onCancelRouteClick = {
                    onScreenEvent(TimerCancellationEvent.CancellationAttempt)
                },
                onRouteResult = { result ->
                    onRouteEvent(RouteEvent.FeedingRouteUpdateRequest(result))
                },
                onGeolocationClick = { mapView ->
                    onGeoLocationClick(mapView, state, geolocationPermissionState)
                },
                onSelectTab = {
                    onFeedingPointEvent(FeedingPointEvent.AnimalTypeChange(it))
                }
            )
        }
    }

    CameraPermissionRequestDialog(isShowing = isCameraPermissionDialogShowing)
    FeedingConfirmationDialog(
        isShowing = isFeedingDialogShowing,
        onAgreeClick = {
            scope.launch { bottomSheetState.hide() }
            onFeedingEvent(FeedingEvent.Start)
        }
    )
    ThankYouConfirmationDialog(state, onScreenEvent)
}

private fun openCamera(
    state: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    context: Context
) {
    if (state.cameraPermissionStatus is PermissionStatus.Granted) {
        onScreenEvent(HomeScreenEvent.CameraEvent.OpenCamera)
    } else {
        context.launchAppSettings()
    }
}

@Composable
private fun OnState(
    state: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    onTimerEvent: (TimerEvent) -> Unit,
) {
    when {
        state.timerState is TimerState.Expired &&
            state.cancellationRequestState == CancellationRequestState.Dismissed -> {
            FeedingExpiredDialog(
                onConfirm = {
                    onTimerEvent(TimerEvent.Disable)
                }
            )
            if (state.deletePhotoItem != null) {
                onScreenEvent(FeedingGalleryEvent.CloseDeletePhotoDialog)
            }
        }
        state.cancellationRequestState is CancellationRequestState.Showing -> FeedingCancellationRequestDialog(
            onConfirm = {
                onScreenEvent(TimerCancellationEvent.CancellationAccepted)
            },
            onDismiss = {
                onScreenEvent(TimerCancellationEvent.CancellationDismissed)
            }
        )
        state.deletePhotoItem != null -> DeletePhotoDialog(
            state.deletePhotoItem,
            onConfirm = {
                onScreenEvent(FeedingGalleryEvent.ConfirmDeletePhoto(state.deletePhotoItem))
            },
            onDismiss = {
                onScreenEvent(FeedingGalleryEvent.CloseDeletePhotoDialog)
            }
        )
        else -> Unit
    }
}

@Composable
private fun ThankYouConfirmationDialog(
    state: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
) {
    if (state.feedingPointState.feedingConfirmationState == FeedingConfirmationState.Showing) {
        ThankYouDialog(onDismiss = {
            onScreenEvent(HomeScreenEvent.DismissThankYouEvent)
        })
    }
}

@Composable
private fun OnBackHandling(
    scope: CoroutineScope,
    bottomSheetState: AnimealBottomSheetState
) {
    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
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
            GpsSettingState.Disabled -> mapView.context.requestGpsByDialog()
            GpsSettingState.Enabled -> mapView.showCurrentLocation(state.locationState.location)
        }
    }
}
