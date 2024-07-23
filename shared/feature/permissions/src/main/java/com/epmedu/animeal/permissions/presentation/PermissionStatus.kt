package com.epmedu.animeal.permissions.presentation

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.accompanist.permissions.PermissionStatus as AccompanistPermissionStatus

sealed interface PermissionStatus {

    /** Permission was granted. */
    data object Granted : PermissionStatus

    /** Permission was denied. */
    data object Denied : PermissionStatus

    /** Permission was either not asked yet or denied and can not be asked again. */
    data object Restricted : PermissionStatus
}

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.toStatus(): PermissionStatus {
    return when (status) {
        is AccompanistPermissionStatus.Granted -> {
            PermissionStatus.Granted
        }
        is AccompanistPermissionStatus.Denied -> {
            when {
                status.shouldShowRationale -> PermissionStatus.Denied
                else -> PermissionStatus.Restricted
            }
        }
    }
}