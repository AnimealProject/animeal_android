package com.epmedu.animeal.feature.more

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.base.ui.BackButton
import com.epmedu.animeal.base.ui.TopBar

@Composable
internal fun ScreenPlaceholder(title: String, onBack: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(title = title) {
                BackButton(onBack)
            }
        },
        content = {}
    )
}