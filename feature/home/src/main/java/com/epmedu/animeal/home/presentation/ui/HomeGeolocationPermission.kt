package com.epmedu.animeal.home.presentation.ui

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeGeolocationPermission(
    homeState: HomeState,
    onScreenEvent: (HomeScreenEvent) -> Unit,
    content: @Composable (PermissionState) -> Unit,
) {
    val geolocationPermissionState = GeoLocationPermission(
        onPermissionResponse = {
            onScreenEvent(HomeScreenEvent.GeolocationPermissionStatusChanged(it))
        },
    )
    if (!homeState.isInitialGeolocationPermissionAsked) {
        LaunchedEffect(Unit) { geolocationPermissionState.launchPermissionRequest() }
    }

    content(geolocationPermissionState)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun GeoLocationPermission(
    onPermissionResponse: (PermissionStatus) -> Unit = {},
    onGrant: () -> Unit = {},
    onDeny: () -> Unit = {},
    onRestrict: () -> Unit = {},
): PermissionState {
    var isPermissionRequested by remember { mutableStateOf(false) }

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = { isPermissionRequested = true }
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