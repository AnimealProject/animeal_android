package com.epmedu.animeal.extensions

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

/**
 * Checks if given permission is declined by user 2 times.
 *
 * @return true if permission is declined
 */
@Composable
@ExperimentalPermissionsApi
fun PermissionState.isPermanentlyDenied() = permissionRequested && !hasPermission && !shouldShowRationale