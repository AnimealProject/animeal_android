package com.epmedu.animeal.home.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FeedingExpiredDialog(
    onConfirm: () -> Unit
) {
    AnimealAlertDialog(
        title = stringResource(id = R.string.feeding_timer_expired_dialog_msg),
        acceptText = stringResource(id = R.string.got_it),
        onConfirm = onConfirm
    )
}

@AnimealPreview
@Composable
private fun FeedingExpiredDialogPreview() {
    AnimealTheme {
        FeedingExpiredDialog(
            onConfirm = {}
        )
    }
}