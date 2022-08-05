package com.epmedu.animeal.foundation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.epmedu.animeal.foundation.theme.AnimealColor.Light

private val DarkColorPalette = darkColors(
    primary = Light.SeaSerpent,
    primaryVariant = Light.SeaSerpent,
    onPrimary = Color.Black,
    secondary = Light.CarminePink,
    secondaryVariant = Light.Gainsboro,
    onSurface = Color.White,
    error = CustomColor.Error
)

private val LightColorPalette = lightColors(
    primary = Light.SeaSerpent,
    primaryVariant = Light.SeaSerpent,
    onPrimary = Color.White,
    background = Color.White,
    secondary = Light.CarminePink,
    secondaryVariant = Light.Gainsboro,
    onSurface = Light.Daintree,
    error = Light.Error
)

@Composable
fun AnimealTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides AnimealRippleTheme,
            content = content
        )
    }
}

private object AnimealRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = RippleTheme.defaultRippleColor(
        contentColor = Light.SeaSerpent,
        lightTheme = !isSystemInDarkTheme(),
    )

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        contentColor = Color.Black,
        lightTheme = !isSystemInDarkTheme(),
    )
}