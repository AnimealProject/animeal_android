package com.epmedu.animeal.more.root

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealSecondaryButton
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.more.root.ui.MoreOption
import com.epmedu.animeal.more.screens
import com.epmedu.animeal.resources.R

@Composable
internal fun MoreScreenUi(
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val showLogoutAlert = rememberSaveable { mutableStateOf(false) }

    if (showLogoutAlert.value) {
        AnimealAlertDialog(
            title = stringResource(id = R.string.profile_logout),
            dismissText = stringResource(id = R.string.cancel),
            acceptText = stringResource(id = R.string.logout),
            onDismissRequest = { showLogoutAlert.value = false },
            onDismiss = { showLogoutAlert.value = false },
            onConfirm = {
                showLogoutAlert.value = false
                onLogout()
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopBar(title = stringResource(id = R.string.more))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(top = 12.dp, bottom = 32.dp)
        ) {
            LazyColumn {
                items(screens) { screen ->
                    MoreOption(
                        title = stringResource(id = screen.title),
                        onClick = { onNavigate(screen.route.name) }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                AnimealSecondaryButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.logout),
                    onClick = {
                        showLogoutAlert.value = true
                    },
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
private fun MoreScreenPreview() {
    AnimealTheme {
        MoreScreenUi(
            onLogout = {},
            onNavigate = {},
        )
    }
}
