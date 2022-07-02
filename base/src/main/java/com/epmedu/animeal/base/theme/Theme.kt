package com.epmedu.animeal.base.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = RobinsEggBlue,
    primaryVariant = RobinsEggBlue,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = RobinsEggBlue,
    primaryVariant = RobinsEggBlue,
    onSurface = Daintree
)

@Composable
fun AnimealTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}