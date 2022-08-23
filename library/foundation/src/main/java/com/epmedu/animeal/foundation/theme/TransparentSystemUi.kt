package com.epmedu.animeal.foundation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun TransparentSystemUi(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

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

    content()
}