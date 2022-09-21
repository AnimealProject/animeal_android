package com.epmedu.animeal.more.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.placeholder.ScreenPlaceholder
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
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

@AnimealPreview
@Composable
private fun AboutScreenUIPreview() {
    AnimealTheme {
        AboutScreenUI(
            onBack = {},
        )
    }
}
