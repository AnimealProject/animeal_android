package com.epmedu.animeal.tabs.more.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun DeleteAccountConfirmationDialog(
    isShowing: MutableState<Boolean>,
    onConfirm: () -> Unit,
) {
    if (isShowing.value) {
        AnimealQuestionDialog(
            title = stringResource(id = R.string.account_delete_confirmation),
            dismissText = stringResource(id = R.string.cancel),
            acceptText = stringResource(id = R.string.delete),
            onDismiss = { isShowing.value = false },
            onConfirm = {
                isShowing.value = false
                onConfirm()
            },
        )
    }
}

@AnimealPreview
@Composable
private fun DeleteAccountConfirmationDialogPreview() {
    val isShowing = remember { mutableStateOf(true) }

    AnimealTheme {
        DeleteAccountConfirmationDialog(isShowing) {}
    }
}