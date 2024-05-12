package com.epmedu.animeal.tabs.more.faq.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.epmedu.animeal.foundation.common.AnimealPopUpScreen
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
        AnimealPopUpScreen(
            painterResource = painterResource(id = R.drawable.ic_faqs_soon),
            titleText = R.string.coming_soon,
            subtitleText = R.string.thank_you_for_visiting,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@AnimealPreview
@Composable
private fun FAQEmptyStatePreview() {
    AnimealTheme {
        FAQEmptyState(onBack = {})
    }
}