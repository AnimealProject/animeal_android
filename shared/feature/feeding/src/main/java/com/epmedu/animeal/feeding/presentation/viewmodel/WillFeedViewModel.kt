package com.epmedu.animeal.feeding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent.ContinueWillFeed
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent.DismissWillFeed
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent.WillFeedClicked
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.CameraPermissionRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.ConfirmationRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.DialogDismissed
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.GeolocationPermissionRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.GpsSettingRequested
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingState
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.permissions.presentation.PermissionStatus
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.GeolocationPermissionAsked
import com.epmedu.animeal.permissions.presentation.handler.PermissionsHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WillFeedViewModel @Inject constructor(
    private val permissionsHandler: PermissionsHandler,
    private val gpsSettingsProvider: GpsSettingsProvider
) : ViewModel(),
    StateDelegate<WillFeedState> by DefaultStateDelegate(DialogDismissed),
    PermissionsHandler by permissionsHandler {

    private var askedToEnableGps = false

    fun handleEvent(event: WillFeedEvent) {
        when (event) {
            WillFeedClicked, ContinueWillFeed -> checkPermissionsAndGps()
            DismissWillFeed -> dismissWillFeed()
        }
    }

    private fun checkPermissionsAndGps() {
        with(permissionsHandler.permissionsStateFlow.value) {
            when {
                cameraPermissionStatus !is PermissionStatus.Granted -> {
                    updateState { CameraPermissionRequested }
                }

                geolocationPermissionStatus !is PermissionStatus.Granted && !isGeolocationPermissionRequestedAgain -> {
                    viewModelScope.handlePermissionEvent(GeolocationPermissionAsked)
                    updateState { GeolocationPermissionRequested }
                }

                gpsSettingsProvider.gpsSettingState == GpsSettingState.Disabled && !askedToEnableGps -> {
                    askedToEnableGps = true
                    updateState { GpsSettingRequested }
                }

                else -> {
                    updateState { ConfirmationRequested }
                }
            }
        }
    }

    private fun dismissWillFeed() {
        updateState { DialogDismissed }
    }
}