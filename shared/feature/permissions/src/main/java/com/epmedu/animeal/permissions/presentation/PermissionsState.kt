package com.epmedu.animeal.permissions.presentation

import com.epmedu.animeal.permissions.presentation.PermissionStatus.Restricted

data class PermissionsState(
    val isGeolocationPermissionAsked: Boolean = false,
    val isGeolocationPermissionRequestedAgain: Boolean = false,
    val isCameraPermissionAsked: Boolean = false,
    val geolocationPermissionStatus: PermissionStatus = Restricted,
    val cameraPermissionStatus: PermissionStatus = Restricted
)
