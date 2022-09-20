package com.epmedu.animeal.signup.finishprofile.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun CancellationDialog(
    onDismissRequest: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AnimealAlertDialog(
        title = stringResource(id = R.string.profile_registration_cancel),
        dismissText = stringResource(id = R.string.no),
        acceptText = stringResource(id = R.string.yes),
        onDismissRequest = onDismissRequest,
        onDismiss = onDismiss,
        onConfirm = onConfirm,
    )
}

@AnimealPreview
@Composable
private fun CancellationDialogPreview() {
    AnimealTheme {
        CancellationDialog(
            onDismissRequest = {},
            onDismiss = {},
            onConfirm = {}
        )
    }
}