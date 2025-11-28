package com.epmedu.animeal.tabs.more.faq.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FAQHeader(onBack: () -> Unit) {
    Column {
        FAQTopBar(onBack)
        HeightSpacer(height = 16.dp)
        Text(
            modifier = Modifier
                .padding(horizontal = 26.dp),
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