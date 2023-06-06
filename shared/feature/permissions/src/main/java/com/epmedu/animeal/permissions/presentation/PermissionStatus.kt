package com.epmedu.animeal.permissions.presentation

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted

sealed interface PermissionStatus {

    /** Permission was granted. */
    object Granted : PermissionStatus

    /** Permission was denied. */
    object Denied : PermissionStatus

    /** Permission was denied and will not being asked again. */
    object Restricted : PermissionStatus
}

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.toStatus(): PermissionStatus =
    if (this.status.isGranted) PermissionStatus.Granted else PermissionStatus.Denied