package com.epmedu.animeal.home.presentation.ui

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeCameraPermission() {
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA,
        onPermissionResult = { }
    )

    when {
        // Permission granted - we don't need to do anymore
        permissionState.status.isGranted -> {
//            onPermissionResponse(PermissionStatus.Granted)
//            onGrant()
        }
//        isPermissionRequested -> {
//            return permissionState
//        }
        // Permission was requested and rationale is not useful
        !permissionState.status.shouldShowRationale -> {
//            onPermissionResponse(PermissionStatus.Restricted)
//            onRestrict()
        }
        // Permission was requested, yet denied
        else -> {
//            onPermissionResponse(PermissionStatus.Denied)
//            onDeny()
        }
    }

    LaunchedEffect(Unit) { permissionState.launchPermissionRequest() }
}