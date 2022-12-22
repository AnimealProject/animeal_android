package com.epmedu.animeal.foundation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.epmedu.animeal.resources.R

object AnimealIcon {
    val copyIcon: Painter
        @Composable
        get() =
            if (isSystemInDarkTheme()) {
                painterResource(id = R.drawable.ic_copy_light)
            } else {
                painterResource(id = R.drawable.ic_copy_dark)
            }
}
