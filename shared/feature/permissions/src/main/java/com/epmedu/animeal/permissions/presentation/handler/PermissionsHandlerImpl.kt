package com.epmedu.animeal.permissions.presentation.handler

import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.permissions.domain.GetAppSettingsUseCase
import com.epmedu.animeal.permissions.domain.UpdateAppSettingsUseCase
import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.CameraPermissionAsked
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.CameraPermissionStatusChanged
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.GeolocationPermissionRationaleShown
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.GeolocationPermissionStatusChanged
import com.epmedu.animeal.permissions.presentation.PermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class PermissionsHandlerImpl(
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val updateAppSettingsUseCase: UpdateAppSettingsUseCase
) : PermissionsHandler,
    StateDelegate<PermissionsState> by DefaultStateDelegate(PermissionsState()) {

    override val permissionsStateFlow: StateFlow<PermissionsState> = stateFlow

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        coroutineScope.launch {
            getAppSettingsUseCase().collectLatest { appSettings ->
                with(appSettings) {
                    updateState {
                        copy(
                            shouldShowGeolocationPermissionRationale = isGeolocationPermissionRationaleShown.not(),
                            isCameraPermissionAsked = isCameraPermissionRequested
                        )
                    }
                }
            }
        }
    }

    override fun CoroutineScope.handlePermissionEvent(event: PermissionsEvent) {
        when (event) {
            is CameraPermissionAsked -> markCameraPermissionAsAsked()
            is GeolocationPermissionRationaleShown -> markGeolocationPermissionRationaleAsShown()
            is GeolocationPermissionStatusChanged -> updateGeolocationPermissionStatus(event)
            is CameraPermissionStatusChanged -> updateCameraPermissionStatus(event)
        }
    }

    private fun CoroutineScope.markGeolocationPermissionRationaleAsShown() {
        launch { updateAppSettingsUseCase { isGeolocationPermissionRationaleShown = true } }
    }

    private fun CoroutineScope.markCameraPermissionAsAsked() {
        if (!state.isCameraPermissionAsked) {
            launch { updateAppSettingsUseCase { isCameraPermissionRequested = true } }
        }
    }

    private fun updateGeolocationPermissionStatus(event: GeolocationPermissionStatusChanged) {
        updateState { copy(geolocationPermissionStatus = event.status) }
    }

    private fun updateCameraPermissionStatus(event: CameraPermissionStatusChanged) {
        updateState { copy(cameraPermissionStatus = event.status) }
    }
}