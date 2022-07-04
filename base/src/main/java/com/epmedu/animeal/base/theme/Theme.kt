package com.epmedu.animeal.base.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.epmedu.animeal.base.theme.AnimealColor.Light

private val DarkColorPalette = darkColors(
    primary = Light.SeaSerpent,
    primaryVariant = Light.SeaSerpent,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = Light.SeaSerpent,
    primaryVariant = Light.SeaSerpent,
    background = Color.White,
    secondary = Light.CarminePink,
    secondaryVariant = Light.Gainsboro,
    onSurface = Light.Daintree,
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