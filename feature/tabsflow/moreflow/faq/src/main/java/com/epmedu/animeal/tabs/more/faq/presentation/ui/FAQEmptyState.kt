package com.epmedu.animeal.tabs.more.faq.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.epmedu.animeal.foundation.placeholder.ComingSoonStub
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FAQEmptyState(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        FAQTopBar(onBack)
        ComingSoonStub {
            Image(
                painter = painterResource(id = R.drawable.ic_questions),
                contentDescription = null
            )
        }
    }
}

@AnimealPreview
@Composable
private fun FAQEmptyStatePreview() {
    AnimealTheme {
        FAQEmptyState(onBack = {})
    }
}