package com.epmedu.animeal.more.account

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.button.AnimealTextButtonWithIcon
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.more.account.ui.LogoutCancellationDialog
import com.epmedu.animeal.resources.R

@Composable
internal fun AccountScreenUI(
    onBack: () -> Unit,
    onLogout: () -> Unit,
) {
    var showLogoutCancellationAlert by rememberSaveable { mutableStateOf(false) }
    if (showLogoutCancellationAlert) {
        val context = LocalContext.current
        LogoutCancellationDialog(
            onDismissRequest = { showLogoutCancellationAlert = false },
            onDismiss = { showLogoutCancellationAlert = false },
            onConfirm = {
                showLogoutCancellationAlert = false
                onLogout()
                Toast.makeText(context, R.string.profile_logout_success, Toast.LENGTH_LONG).show()
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            TopBar(title = stringResource(id = R.string.page_account)) {
                BackButton(onBack)
            }
        }
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            AnimealTextButtonWithIcon(
                icon = painterResource(id = R.drawable.ic_delete),
                text = stringResource(id = R.string.account_delete),
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Start,
                onClick = { /* TODO implement account deletion */ }
            )

            Spacer(modifier = Modifier.weight(1f))

            AnimealSecondaryButtonOutlined(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                text = stringResource(id = R.string.logout),
                onClick = { showLogoutCancellationAlert = true },
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AccountScreenPreview() {
    AnimealTheme {
        AccountScreenUI(
            onBack = {},
            onLogout = {},
        )
    }
}
