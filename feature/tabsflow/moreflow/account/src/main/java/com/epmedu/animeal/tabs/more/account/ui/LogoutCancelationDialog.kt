package com.epmedu.animeal.tabs.more.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.resources.R

@Composable
internal fun LogoutCancellationDialog(
    onDismissRequest: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) = AnimealAlertDialog(
    title = stringResource(id = R.string.profile_logout_cancel),
    dismissText = stringResource(id = R.string.cancel),
    acceptText = stringResource(id = R.string.logout),
    onDismissRequest = onDismissRequest,
    onDismiss = onDismiss,
    onConfirm = onConfirm,
)
