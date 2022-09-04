package com.epmedu.animeal.more.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar

@Composable
internal fun ScreenPlaceholder(
    title: String,
    content: @Composable (PaddingValues) -> Unit = {},
    onBack: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = title,
                modifier = Modifier.statusBarsPadding()
            ) {
                BackButton(onBack)
            }
        },
        content = content
    )
}