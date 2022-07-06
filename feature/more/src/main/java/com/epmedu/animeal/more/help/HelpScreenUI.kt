package com.epmedu.animeal.more.help

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.more.ui.common.ScreenPlaceholder
import com.epmedu.animeal.resources.R

@Composable
internal fun HelpScreenUI(
    onBack: () -> Unit,
) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.page_help),
        onBack = onBack,
    )
}

@Preview
@Composable
fun HelpScreenUIPreview() {
    AnimealTheme {
        HelpScreenUI(
            onBack = {},
        )
    }
}
