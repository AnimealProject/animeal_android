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
internal fun LogoutConfirmationDialog(
    isShowing: MutableState<Boolean>,
    onConfirm: () -> Unit,
) {
    if (isShowing.value) {
        AnimealQuestionDialog(
            title = stringResource(id = R.string.profile_logout_cancel),
            dismissText = stringResource(id = R.string.cancel),
            acceptText = stringResource(id = R.string.logout),
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
private fun LogoutConfirmationDialogPreview() {
    val isShowing = remember { mutableStateOf(true) }

    AnimealTheme {
        LogoutConfirmationDialog(isShowing) {}
    }
}
