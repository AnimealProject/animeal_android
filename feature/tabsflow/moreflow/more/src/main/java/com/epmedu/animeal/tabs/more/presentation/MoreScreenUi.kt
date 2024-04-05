package com.epmedu.animeal.tabs.more.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.presentation.model.MoreOption
import com.epmedu.animeal.tabs.more.presentation.ui.FeedingsIndicator
import com.epmedu.animeal.tabs.more.presentation.ui.MoreOption
import com.epmedu.animeal.tabs.more.presentation.viewmodel.MoreState
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun MoreScreenUi(
    state: MoreState,
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
                items(state.options) { option ->
                    MoreOption(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(text = stringResource(id = option.stringResource))

                                if (option is MoreOption.Feedings && option.isIndicatorEnabled) {
                                    FeedingsIndicator()
                                }
                            }
                        },
                        onClick = { onNavigate(option.route.name) }
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
            state = MoreState(
                options = persistentListOf(
                    MoreOption.Profile,
                    MoreOption.Feedings(),
                    MoreOption.Feedings(isIndicatorEnabled = true)
                )
            ),
            onNavigate = {}
        )
    }
}
