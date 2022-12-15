package com.epmedu.animeal.home.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
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
import com.epmedu.animeal.home.presentation.HomeScreenEvent.*
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.ui.HomeGeolocationPermission
import com.epmedu.animeal.home.presentation.ui.HomeMapbox
import com.epmedu.animeal.home.presentation.ui.showCurrentLocation
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Suppress("LongMethod")
internal fun HomeScreenUI(
    state: HomeState,
    bottomSheetState: AnimealBottomSheetState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
) {
    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()

    val scope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    BackHandler(enabled = state.willFeedState.isDialogShowing) {
        scope.launch { onScreenEvent(DismissWillFeedDialog) }
    }

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
                onClick = { onScreenEvent(ShowWillFeedDialog) }
            )
        }
    ) {
        HomeGeolocationPermission(
            homeState = state,
            onScreenEvent = onScreenEvent,
        ) { geolocationPermissionState ->
            HomeMapbox(
                state = state,
                onFeedingPointSelect = { onScreenEvent(FeedingPointSelected(it.id)) },
                onGeolocationClick = { mapView ->
                    when (state.geolocationPermissionStatus) {
                        PermissionStatus.Restricted -> mapView.context.launchAppSettings()
                        PermissionStatus.Denied -> geolocationPermissionState.launchPermissionRequest()
                        PermissionStatus.Granted -> when (state.gpsSettingState) {
                            GpsSettingState.Disabled -> mapView.context.launchGpsSettings()
                            GpsSettingState.Enabled -> mapView.showCurrentLocation(state.locationState.location)
                        }
                    }
                },
                onMapInteraction = { if (!bottomSheetState.isHiding) scope.launch { bottomSheetState.show() } }
            )
        }
    }

    WillFeedConfirmationDialog(state, onScreenEvent)
}

@Composable
private fun WillFeedConfirmationDialog(state: HomeState, onScreenEvent: (HomeScreenEvent) -> Unit) {
    if (state.willFeedState.isDialogShowing) {
        FeedConfirmationDialog(
            onAgreeClick = { onScreenEvent(DismissWillFeedDialog) },
            onCancelClick = { onScreenEvent(DismissWillFeedDialog) }
        )
    }
}