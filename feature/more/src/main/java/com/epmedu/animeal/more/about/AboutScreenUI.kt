package com.epmedu.animeal.more.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.more.ui.common.ScreenPlaceholder
import com.epmedu.animeal.resources.R

@Composable
internal fun AboutScreenUI(
    onBack: () -> Unit,
) {
    ScreenPlaceholder(
        title = stringResource(id = R.string.page_about),
        onBack = onBack,
    )
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun AboutScreenUIPreview() {
    AnimealTheme {
        AboutScreenUI(
            onBack = {},
        )
    }
}
