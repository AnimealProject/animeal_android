package com.epmedu.animeal.more.help

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.more.ui.common.ScreenPlaceholder
import com.epmedu.animeal.resources.R

@Composable
internal fun HelpScreenUI(
    helpState: HelpState,
    onBack: () -> Unit,
) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.page_help),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(32.dp),
                    text = stringResource(
                        id = R.string.help_page_app_version,
                        helpState.appVersionName
                    ),
                    style = MaterialTheme.typography.caption
                )
            }
        },
        onBack = onBack,
    )
}

@AnimealPreview
@Composable
private fun HelpScreenUIPreview() {
    AnimealTheme {
        HelpScreenUI(
            helpState = HelpState(),
            onBack = {},
        )
    }
}
