package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.Cross: ImageVector
    get() {
        if (_Cross != null) {
            return _Cross!!
        }
        _Cross = ImageVector.Builder(
            name = "Colored.Cross",
            defaultWidth = 20.dp,
            defaultHeight = 20.dp,
            viewportWidth = 20f,
            viewportHeight = 20f
        ).apply {
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(1.459f, 1.467f)
                lineTo(1.459f, 1.467f)
                arcTo(2.017f, 2.063f, 90f, isMoreThanHalf = false, isPositiveArc = true, 4.376f, 1.467f)
                lineTo(18.116f, 14.903f)
                arcTo(2.017f, 2.063f, 90f, isMoreThanHalf = false, isPositiveArc = true, 18.116f, 17.756f)
                lineTo(18.116f, 17.756f)
                arcTo(2.017f, 2.063f, 90f, isMoreThanHalf = false, isPositiveArc = true, 15.199f, 17.756f)
                lineTo(1.459f, 4.32f)
                arcTo(2.063f, 2.017f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.459f, 1.467f)
                close()
            }
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(18.542f, 1.426f)
                lineTo(18.542f, 1.426f)
                arcTo(2.017f, 2.063f, 90f, isMoreThanHalf = false, isPositiveArc = true, 18.542f, 4.279f)
                lineTo(4.989f, 17.532f)
                arcTo(2.017f, 2.063f, 90f, isMoreThanHalf = false, isPositiveArc = true, 2.072f, 17.532f)
                lineTo(2.072f, 17.532f)
                arcTo(2.017f, 2.063f, 90f, isMoreThanHalf = false, isPositiveArc = true, 2.072f, 14.679f)
                lineTo(15.624f, 1.426f)
                arcTo(2.063f, 2.017f, 0f, isMoreThanHalf = false, isPositiveArc = true, 18.542f, 1.426f)
                close()
            }
        }.build()

        return _Cross!!
    }

@Suppress("ObjectPropertyName")
private var _Cross: ImageVector? = null
