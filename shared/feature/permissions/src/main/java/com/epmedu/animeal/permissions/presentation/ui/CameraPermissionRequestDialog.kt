package com.epmedu.animeal.permissions.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.extensions.launchAppSettings
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.resources.R

@Composable
fun CameraPermissionRequestDialog(isShowing: MutableState<Boolean>) {
    val context = LocalContext.current

    if (isShowing.value) {
        AnimealQuestionDialog(
            title = stringResource(id = R.string.camera_permission_request),
            dismissText = stringResource(id = R.string.no),
            acceptText = stringResource(id = R.string.open_settings),
            onDismiss = { isShowing.value = false },
            onConfirm = {
                isShowing.value = false
                context.launchAppSettings()
            },
        )
    }
}