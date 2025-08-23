package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.SignInGuest: ImageVector
    get() {
        if (_SignInGuest != null) {
            return _SignInGuest!!
        }
        _SignInGuest = ImageVector.Builder(
            name = "Colored.SignInGuest",
            defaultWidth = 17.dp,
            defaultHeight = 16.dp,
            viewportWidth = 17f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF03BFD7)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(2.093f, 14f)
                curveTo(3.062f, 11.584f, 5.404f, 10f, 8.007f, 10f)
                curveTo(10.61f, 10f, 12.952f, 11.584f, 13.922f, 14f)
                horizontalLineTo(2.095f)
                horizontalLineTo(2.093f)
                close()
                moveTo(4.867f, 5f)
                curveTo(4.895f, 3.429f, 6.115f, 2.137f, 7.683f, 2.02f)
                curveTo(7.898f, 1.993f, 8.116f, 1.993f, 8.332f, 2.02f)
                curveTo(9.915f, 2.263f, 11.031f, 3.703f, 10.869f, 5.297f)
                curveTo(10.708f, 6.891f, 9.327f, 8.079f, 7.727f, 8f)
                curveTo(6.127f, 7.92f, 4.87f, 6.602f, 4.867f, 5f)
                lineTo(4.867f, 5f)
                close()
                moveTo(15.894f, 13.343f)
                curveTo(15.112f, 11.26f, 13.506f, 9.591f, 11.455f, 8.73f)
                curveTo(12.572f, 7.812f, 13.225f, 6.446f, 13.238f, 5f)
                curveTo(13.196f, 2.407f, 11.202f, 0.265f, 8.619f, 0.038f)
                curveTo(8.21f, -0.013f, 7.797f, -0.013f, 7.389f, 0.038f)
                curveTo(4.807f, 0.267f, 2.815f, 2.408f, 2.774f, 5f)
                curveTo(2.786f, 6.446f, 3.439f, 7.812f, 4.556f, 8.73f)
                curveTo(2.504f, 9.591f, 0.898f, 11.26f, 0.115f, 13.343f)
                curveTo(-0.106f, 13.955f, -0.005f, 14.638f, 0.386f, 15.159f)
                curveTo(0.791f, 15.693f, 1.424f, 16.005f, 2.095f, 16f)
                horizontalLineTo(13.924f)
                curveTo(14.593f, 16.004f, 15.225f, 15.693f, 15.63f, 15.159f)
                curveTo(16.02f, 14.637f, 16.119f, 13.954f, 15.894f, 13.343f)
                horizontalLineTo(15.894f)
                close()
            }
        }.build()

        return _SignInGuest!!
    }

@Suppress("ObjectPropertyName")
private var _SignInGuest: ImageVector? = null
