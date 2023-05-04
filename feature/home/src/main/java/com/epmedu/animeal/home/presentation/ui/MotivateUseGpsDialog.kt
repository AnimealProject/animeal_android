package com.epmedu.animeal.home.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.resources.R

@Composable
fun MotivateUseGpsDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AnimealQuestionDialog(
        title = stringResource(id = R.string.geolocation_permission_title),
        dismissText = stringResource(id = R.string.no_thanks),
        acceptText = stringResource(id = R.string.open_settings),
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}