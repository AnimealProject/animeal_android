package com.epmedu.animeal.home.presentation.thankyou

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.home.presentation.thankyou.ui.BackToHomeButton
import com.epmedu.animeal.home.presentation.thankyou.ui.ThankYouContent

@Composable
internal fun ThankYouScreenUI(
    modifier: Modifier = Modifier,
    onBackToHome: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ThankYouContent()
        }
        BackToHomeButton(onBackToHome)
    }
}

@AnimealPreview
@Composable
private fun ThankYouScreenPreview() {
    AnimealTheme {
        ThankYouScreenUI(
            onBackToHome = {},
        )
    }
}