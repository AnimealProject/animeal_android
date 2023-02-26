package com.epmedu.animeal.foundation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
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
    val colors = MaterialTheme.colors

    LaunchedEffect(Unit) {
        systemUiController.run {
            setNavigationBarColor(
                color = Color.Transparent
            )
            setSystemBarsColor(
                color = colors.background,
                darkIcons = darkTheme.not()
            )
        }
    }

    content()
}