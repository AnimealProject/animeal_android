package com.epmedu.animeal.more.help

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.placeholder.ScreenPlaceholder
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.more.help.viewmodel.HelpState
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

@Preview
@Composable
private fun HelpScreenUIPreview() {
    AnimealTheme {
        HelpScreenUI(
            helpState = HelpState(),
            onBack = {},
        )
    }
}
