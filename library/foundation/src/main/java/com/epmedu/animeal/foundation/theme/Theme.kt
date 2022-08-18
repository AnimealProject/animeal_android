package com.epmedu.animeal.foundation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.epmedu.animeal.foundation.theme.AnimealColor.Light
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        LaunchedEffect(Unit) {
            systemUiController.run {
                setNavigationBarColor(
                    color = Color.Transparent
                )
                setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = darkTheme.not()
                )
            }
        }

        Surface(content = content)
    }
}