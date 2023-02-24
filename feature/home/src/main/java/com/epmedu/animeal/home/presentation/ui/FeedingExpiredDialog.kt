package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FeedingExpiredDialog(
    onConfirm: () -> Unit
) {
    AnimealAlertDialog(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        title = stringResource(id = R.string.feeding_timer_expired),
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