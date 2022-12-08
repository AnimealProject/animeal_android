package com.epmedu.animeal.home.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.extensions.launchGpsSettings
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.HomeScreenEvent.*
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.ui.HomeGeolocationPermission
import com.epmedu.animeal.home.presentation.ui.HomeMapbox
import com.epmedu.animeal.home.presentation.ui.bottomsheet.HomeBottomSheet
import com.epmedu.animeal.home.presentation.ui.bottomsheet.HomeBottomSheetState
import com.epmedu.animeal.home.presentation.ui.showCurrentLocation
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Suppress("LongMethod")
internal fun HomeScreenUI(
    state: HomeState,
    bottomSheetState: HomeBottomSheetState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    BackHandler(enabled = state.willFeedState.isDialogShowing) {
        scope.launch { onScreenEvent(DismissWillFeedDialog) }
    }

    HomeBottomSheet(
        homeState = state,
        bottomSheetState = bottomSheetState,
        onScreenEvent = onScreenEvent,
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
                            GpsSettingState.Enabled -> mapView.showCurrentLocation(state.currentLocation)
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
