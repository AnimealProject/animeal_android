package com.epmedu.animeal.more.root

import androidx.compose.foundation.layout.*
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
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(title = stringResource(id = R.string.more)) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Spacer(modifier = Modifier.height(48.dp))
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
                text = stringResource(id = R.string.logout),
                onClick = onLogout,
                modifier = Modifier.padding(start = 44.dp, bottom = 44.dp)
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
