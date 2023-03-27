package com.epmedu.animeal.debugmenu.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
internal fun DebugMenuButton(
    text: String,
    onClick: () -> Unit
) {
    AnimealButton(onClick = onClick) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun DebugMenuButtonPreview() {
    AnimealTheme {
        DebugMenuButton(text = "Button", onClick = {})
    }
}