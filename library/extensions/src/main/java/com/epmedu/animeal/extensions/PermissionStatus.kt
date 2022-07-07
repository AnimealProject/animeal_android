package com.epmedu.animeal.extensions

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

/**
 * Checks if given permission is declined by user 2 times.
 *
 * @return true if permission is declined
 */
@Composable
@ExperimentalPermissionsApi
fun PermissionStatus.isPermanentlyDenied() = !isGranted && !shouldShowRationale