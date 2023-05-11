package com.epmedu.animeal.home.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.resources.R

@Composable
internal fun CameraPermissionRequestDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AnimealQuestionDialog(
        title = stringResource(id = R.string.camera_permission_request),
        dismissText = stringResource(id = R.string.no),
        acceptText = stringResource(id = R.string.open_settings),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
    )
}