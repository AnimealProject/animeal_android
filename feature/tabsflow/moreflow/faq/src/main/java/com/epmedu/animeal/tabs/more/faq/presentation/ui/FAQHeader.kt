package com.epmedu.animeal.tabs.more.faq.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun FAQHeader(onBack: () -> Unit) {
    Column {
        TopBar(
            title = stringResource(id = R.string.page_faq),
            navigationIcon = {
                BackButton(onClick = onBack)
            }
        )
        Text(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 24.dp)
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.faq_subtitle),
            style = MaterialTheme.typography.subtitle1,
        )
    }
}

@AnimealPreview
@Composable
private fun FAQHeaderPreview() {
    AnimealTheme {
        FAQHeader(
            onBack = {}
        )
    }
}