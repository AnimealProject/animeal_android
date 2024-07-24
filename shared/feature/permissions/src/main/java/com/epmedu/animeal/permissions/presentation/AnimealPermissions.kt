package com.epmedu.animeal.permissions.presentation

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.CAMERA
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.RESUMED
import com.epmedu.animeal.permissions.presentation.PermissionStatus.Restricted
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.CameraPermissionStatusChanged
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.GeolocationPermissionStatusChanged
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AnimealPermissions(
    lifecycleState: Lifecycle.State,
    onEvent: (PermissionsEvent) -> Unit,
    content: @Composable (PermissionState) -> Unit,
) {
    val geolocationPermissionState = rememberPermissionState(permission = ACCESS_FINE_LOCATION)
    val cameraPermissionState = rememberPermissionState(permission = CAMERA)
    val geolocationPermissionStatus = geolocationPermissionState.toStatus()
    val cameraPermissionStatus = cameraPermissionState.toStatus()

    LaunchedEffect(geolocationPermissionStatus) {
        onEvent(GeolocationPermissionStatusChanged(geolocationPermissionStatus))
    }

    LaunchedEffect(cameraPermissionStatus) {
        onEvent(CameraPermissionStatusChanged(cameraPermissionStatus))
    }

    LaunchedEffect(geolocationPermissionStatus, lifecycleState) {
        if (lifecycleState.isAtLeast(RESUMED) && geolocationPermissionStatus is Restricted) {
            geolocationPermissionState.launchPermissionRequest()
        }
    }
    content(geolocationPermissionState)
}