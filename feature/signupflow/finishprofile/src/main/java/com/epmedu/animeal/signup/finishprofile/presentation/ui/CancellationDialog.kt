package com.epmedu.animeal.signup.finishprofile.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
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

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
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