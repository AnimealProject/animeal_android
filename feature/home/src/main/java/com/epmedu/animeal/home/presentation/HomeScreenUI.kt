package com.epmedu.animeal.home.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.model.WillFeedState
import com.epmedu.animeal.home.presentation.ui.HomeGeolocationPermission
import com.epmedu.animeal.home.presentation.ui.HomeMapbox
import com.epmedu.animeal.home.presentation.ui.showCurrentLocation
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
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
    onScreenEvent: (HomeScreenEvent) -> Unit,
    onTimerExpire: () -> Unit
) {
    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()
    val scope = rememberCoroutineScope()

    scope.launch {
        if (state.feedingRouteState !is FeedingRouteState.Disabled && !bottomSheetState.isHiding) {
            bottomSheetState.hide()
        }
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
                    feedingPoint = FeedingPointModel(feedingPoint),
                    contentAlpha = contentAlpha,
                    modifier = Modifier.wrapContentHeight(),
                    onFavouriteChange = {
                        onScreenEvent(HomeScreenEvent.FeedingPointFavouriteChange(isFavourite = it))
                    }
                )
            }
        },
        sheetControls = {
            if (bottomSheetState.isVisible) {
                FeedingPointActionButton(
                    alpha = buttonAlpha,
                    enabled = state.currentFeedingPoint?.animalStatus == AnimalState.RED,
                    onClick = { onScreenEvent(HomeScreenEvent.WillFeedEvent.ShowWillFeedDialog) }
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
                onFeedingPointSelect = { onScreenEvent(HomeScreenEvent.FeedingPointSelected(it.id)) },
                onMapInteraction = {
                    if (bottomSheetState.isExpanding && !state.feedingRouteState.isRouteActive) {
                        scope.launch { bottomSheetState.show() }
                    }
                },
                onCancelRouteClick = {
                    onScreenEvent(HomeScreenEvent.RouteEvent.FeedingRouteCancellationRequest)
                },
                onRouteResult = { result ->
                    onScreenEvent(HomeScreenEvent.RouteEvent.FeedingRouteUpdateRequest(result))
                },
                onGeolocationClick = { mapView ->
                    onGeoLocationClick(mapView, state, geolocationPermissionState)
                }
            )
        }
    }

    WillFeedConfirmationDialog(state, onScreenEvent, onTimerExpire)
}

@Composable
private fun WillFeedConfirmationDialog(
    state: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    onTimerExpire: () -> Unit
) {
    if (state.willFeedState is WillFeedState.Showing) {
        FeedConfirmationDialog(
            onAgreeClick = {
                onScreenEvent(HomeScreenEvent.WillFeedEvent.DismissWillFeedDialog)
                onScreenEvent(HomeScreenEvent.RouteEvent.FeedingRouteStartRequest(onTimerExpire))
            },
            onCancelClick = { onScreenEvent(HomeScreenEvent.WillFeedEvent.DismissWillFeedDialog) }
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
        scope.launch { onScreenEvent(HomeScreenEvent.WillFeedEvent.DismissWillFeedDialog) }
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
