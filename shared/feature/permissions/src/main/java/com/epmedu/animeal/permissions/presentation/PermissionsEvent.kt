package com.epmedu.animeal.permissions.presentation

sealed interface PermissionsEvent {
    object GeolocationPermissionRationaleShown : PermissionsEvent
    object CameraPermissionAsked : PermissionsEvent

    data class GeolocationPermissionStatusChanged(val status: PermissionStatus) : PermissionsEvent
    data class CameraPermissionStatusChanged(val status: PermissionStatus) : PermissionsEvent
}