package com.epmedu.animeal.tabs.more.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.button.AnimealTextButtonWithIcon
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.account.AccountScreenEvent.Delete
import com.epmedu.animeal.tabs.more.account.AccountScreenEvent.Logout
import com.epmedu.animeal.tabs.more.account.ui.DeleteAccountConfirmationDialog
import com.epmedu.animeal.tabs.more.account.ui.LogoutConfirmationDialog

@Composable
internal fun AccountScreenUI(
    onBack: () -> Unit,
    onEvent: (AccountScreenEvent) -> Unit
) {
    val isLogoutConfirmationDialogShowing = rememberSaveable { mutableStateOf(false) }
    val isDeleteAccountConfirmationDialogShowing = rememberSaveable { mutableStateOf(false) }

    LogoutConfirmationDialog(
        isShowing = isLogoutConfirmationDialogShowing,
        onConfirm = { onEvent(Logout) }
    )

    DeleteAccountConfirmationDialog(
        isShowing = isDeleteAccountConfirmationDialogShowing,
        onConfirm = { onEvent(Delete) }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            TopBar(title = stringResource(id = R.string.page_account)) {
                BackButton(onBack)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            AnimealTextButtonWithIcon(
                icon = painterResource(id = R.drawable.ic_delete),
                text = stringResource(id = R.string.account_delete),
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Start,
                onClick = { isDeleteAccountConfirmationDialogShowing.value = true }
            )

            Spacer(modifier = Modifier.weight(1f))

            AnimealSecondaryButtonOutlined(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                text = stringResource(id = R.string.logout),
                onClick = { isLogoutConfirmationDialogShowing.value = true },
            )
        }
    }
}

@AnimealPreview
@Composable
private fun AccountScreenPreview() {
    AnimealTheme {
        AccountScreenUI(
            onBack = {},
            onEvent = {},
        )
    }
}
