package com.epmedu.animeal.more

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.common.route.MoreRoute
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.more.ui.MoreOption
import com.epmedu.animeal.resources.R

@Composable
internal fun MoreScreenUi(
    onNavigate: (String) -> Unit,
) {
    val options = listOf(
        R.string.page_profile to MoreRoute.Profile,
        R.string.page_donate to MoreRoute.Donate,
        R.string.page_help to MoreRoute.Help,
        R.string.page_about_detailed to MoreRoute.About,
        R.string.page_account to MoreRoute.Account,
    )

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
                items(options) {
                    MoreOption(
                        title = stringResource(id = it.first),
                        onClick = { onNavigate(it.second.name) }
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
