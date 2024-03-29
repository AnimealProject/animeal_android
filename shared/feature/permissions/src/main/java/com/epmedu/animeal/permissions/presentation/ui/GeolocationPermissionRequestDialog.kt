package com.epmedu.animeal.permissions.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun GeolocationPermissionRequestDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val context = LocalContext.current

    AnimealQuestionDialog(
        title = stringResource(id = R.string.geolocation_permission_title),
        dismissText = stringResource(id = R.string.no_thanks),
        acceptText = stringResource(id = R.string.open_settings),
        onDismiss = onDismiss,
        onConfirm = {
            context.launchAppSettings()
            onConfirm()
        }
    )
}

@AnimealPreview
@Composable
private fun GeolocationPermissionRequestDialogPreview() {
    AnimealTheme {
        GeolocationPermissionRequestDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}