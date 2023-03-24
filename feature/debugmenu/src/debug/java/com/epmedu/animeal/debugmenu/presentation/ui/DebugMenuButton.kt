package com.epmedu.animeal.debugmenu.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.epmedu.animeal.foundation.button.AnimealButton

@Composable
internal fun DebugMenuButton(
    text: String,
    onClick: () -> Unit
) {
    AnimealButton(onClick = onClick) {
        Text(text = text)
    }
}