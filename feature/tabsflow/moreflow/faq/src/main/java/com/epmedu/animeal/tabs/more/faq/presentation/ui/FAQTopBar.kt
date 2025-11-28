package com.epmedu.animeal.tabs.more.faq.presentation.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun FAQTopBar(onBack: () -> Unit) {
    TopBar(
        title = stringResource(id = R.string.page_faq),
        modifier = Modifier.statusBarsPadding(),
        navigationIcon = {
            BackButton(onClick = onBack)
        }
    )
}

@AnimealPreview
@Composable
private fun FAQTopBarPreview() {
    AnimealTheme {
        FAQTopBar(onBack = {})
    }
}