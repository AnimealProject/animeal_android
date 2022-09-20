package com.epmedu.animeal.more.root

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.more.root.ui.MoreOption
import com.epmedu.animeal.more.screens
import com.epmedu.animeal.resources.R

@Composable
internal fun MoreScreenUi(
    onNavigate: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .bottomBarPadding(),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.more),
                modifier = Modifier.statusBarsPadding()
            )
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
        }
    }
}

@AnimealPreview
@Composable
private fun MoreScreenPreview() {
    AnimealTheme {
        MoreScreenUi(
            onNavigate = {},
        )
    }
}
