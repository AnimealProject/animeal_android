package com.epmedu.animeal.more.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealShortButton
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
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
            AnimealShortButton(
                modifier = Modifier.padding(start = 24.dp),
                text = stringResource(id = R.string.logout),
                onClick = onLogout,
            )
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
