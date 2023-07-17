package com.epmedu.animeal.home.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FeedingCancellationRequestDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AnimealQuestionDialog(
        title = stringResource(id = R.string.feeding_timer_suspended),
        dismissText = stringResource(id = R.string.no),
        acceptText = stringResource(id = R.string.yes),
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}

@AnimealPreview
@Composable
private fun FeedingSuspendedDialogPreview() {
    AnimealTheme {
        FeedingCancellationRequestDialog(
            onConfirm = {},
            onDismiss = {}
        )
    }
}