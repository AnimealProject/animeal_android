package com.epmedu.animeal.permissions.presentation.handler

import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.permissions.domain.GetCameraPermissionRequestedUseCase
import com.epmedu.animeal.permissions.domain.GetGeolocationPermissionRequestedUseCase
import com.epmedu.animeal.permissions.domain.UpdateCameraPermissionRequestUseCase
import com.epmedu.animeal.permissions.domain.UpdateGeolocationPermissionRequestedUseCase
import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.CameraPermissionAsked
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.CameraPermissionStatusChanged
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.GeolocationPermissionAsked
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.GeolocationPermissionStatusChanged
import com.epmedu.animeal.permissions.presentation.PermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class PermissionsHandlerImpl(
    private val getGeolocationPermissionRequestedUseCase: GetGeolocationPermissionRequestedUseCase,
    private val updateGeolocationPermissionRequestedUseCase: UpdateGeolocationPermissionRequestedUseCase,
    private val getCameraPermissionRequestedUseCase: GetCameraPermissionRequestedUseCase,
    private val updateCameraPermissionRequestUseCase: UpdateCameraPermissionRequestUseCase,
) : PermissionsHandler,
    StateDelegate<PermissionsState> by DefaultStateDelegate(PermissionsState()) {

    override val permissionsStateFlow: StateFlow<PermissionsState> = stateFlow

    init {
        updateState {
            copy(
                isGeolocationPermissionAsked = getGeolocationPermissionRequestedUseCase(),
                isCameraPermissionAsked = getCameraPermissionRequestedUseCase()
            )
        }
    }

    override fun CoroutineScope.handlePermissionEvent(event: PermissionsEvent) {
        when (event) {
            is CameraPermissionAsked -> markCameraPermissionAsAsked()
            is GeolocationPermissionAsked -> markGeolocationPermissionAsAsked()
            is GeolocationPermissionStatusChanged -> updateGeolocationPermissionStatus(event)
            is CameraPermissionStatusChanged -> updateCameraPermissionStatus(event)
        }
    }

    private fun CoroutineScope.markGeolocationPermissionAsAsked() {
        if (!state.isGeolocationPermissionAsked) {
            launch { updateGeolocationPermissionRequestedUseCase(true) }
            updateState { copy(isGeolocationPermissionAsked = true) }
        }
    }

    private fun CoroutineScope.markCameraPermissionAsAsked() {
        if (!state.isCameraPermissionAsked) {
            launch { updateCameraPermissionRequestUseCase(true) }
            updateState { copy(isCameraPermissionAsked = true) }
        }
    }

    private fun updateGeolocationPermissionStatus(event: GeolocationPermissionStatusChanged) {
        updateState { copy(geolocationPermissionStatus = event.status) }
    }

    private fun updateCameraPermissionStatus(event: CameraPermissionStatusChanged) {
        updateState { copy(cameraPermissionStatus = event.status) }
    }
}