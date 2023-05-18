package com.epmedu.animeal.permissions.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.resources.R

@Composable
fun GeolocationPermissionRequestDialog(
    isShowing: MutableState<Boolean>
) {
    val context = LocalContext.current

    if (isShowing.value) {
        AnimealQuestionDialog(
            title = stringResource(id = R.string.geolocation_permission_title),
            dismissText = stringResource(id = R.string.no_thanks),
            acceptText = stringResource(id = R.string.open_settings),
            onDismiss = { isShowing.value = false },
            onConfirm = {
                isShowing.value = false
                context.launchAppSettings()
            }
        )
    }
}