package com.epmedu.animeal.permissions.presentation

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.RESUMED
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.CameraPermissionAsked
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.CameraPermissionStatusChanged
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.GeolocationPermissionAsked
import com.epmedu.animeal.permissions.presentation.PermissionsEvent.GeolocationPermissionStatusChanged
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AnimealPermissions(
    permissionsState: PermissionsState,
    lifecycleState: Lifecycle.State,
    onEvent: (PermissionsEvent) -> Unit,
    content: @Composable (PermissionState) -> Unit,
) {
    val geolocationPermissionState = getGeolocationPermissionState(
        onPermissionResponse = { permissionStatus ->
            onEvent(GeolocationPermissionStatusChanged(permissionStatus))
        },
        onPermissionRequest = {
            onEvent(GeolocationPermissionAsked)
        }
    )
    val cameraPermissionState = getCameraPermissionState(
        onPermissionResponse = { permissionStatus ->
            onEvent(CameraPermissionStatusChanged(permissionStatus))
        },
        onPermissionRequest = {
            onEvent(CameraPermissionAsked)
        }
    )

    with(permissionsState) {
        LaunchedEffect(
            key1 = isGeolocationPermissionAsked,
            key2 = isCameraPermissionAsked,
            key3 = lifecycleState
        ) {
            if (lifecycleState.isAtLeast(RESUMED)) {
                when {
                    !isGeolocationPermissionAsked -> geolocationPermissionState.launchPermissionRequest()
                    !isCameraPermissionAsked -> cameraPermissionState.launchPermissionRequest()
                }
            }
        }
    }
    content(geolocationPermissionState)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun getGeolocationPermissionState(
    onPermissionResponse: (PermissionStatus) -> Unit = {},
    onPermissionRequest: () -> Unit,
    onGrant: () -> Unit = {},
    onDeny: () -> Unit = {},
    onRestrict: () -> Unit = {},
): PermissionState {
    var isPermissionRequested by remember { mutableStateOf(false) }

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = {
            onPermissionRequest()
            isPermissionRequested = true
        }
    )

    when {
        // Permission granted - we don't need to do anymore
        permissionState.status.isGranted -> {
            onPermissionResponse(PermissionStatus.Granted)
            onGrant()
        }

        isPermissionRequested -> {
            return permissionState
        }
        // Permission was requested and rationale is not useful
        !permissionState.status.shouldShowRationale -> {
            onPermissionResponse(PermissionStatus.Restricted)
            onRestrict()
        }
        // Permission was requested, yet denied
        else -> {
            onPermissionResponse(PermissionStatus.Denied)
            onDeny()
        }
    }

    return permissionState
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun getCameraPermissionState(
    onPermissionResponse: (PermissionStatus) -> Unit = {},
    onPermissionRequest: () -> Unit,
    onGrant: () -> Unit = {},
    onDeny: () -> Unit = {},
    onRestrict: () -> Unit = {}
): PermissionState {
    var isPermissionRequested by remember { mutableStateOf(false) }

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA,
        onPermissionResult = {
            onPermissionRequest()
            isPermissionRequested = true
        }
    )

    when {
        // Permission granted - we don't need to do anymore
        permissionState.status.isGranted -> {
            onPermissionResponse(PermissionStatus.Granted)
            onGrant()
        }

        isPermissionRequested -> {
            return permissionState
        }
        // Permission was requested and rationale is not useful
        !permissionState.status.shouldShowRationale -> {
            onPermissionResponse(PermissionStatus.Restricted)
            onRestrict()
        }
        // Permission was requested, yet denied
        else -> {
            onPermissionResponse(PermissionStatus.Denied)
            onDeny()
        }
    }

    return permissionState
}