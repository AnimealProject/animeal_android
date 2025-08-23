package com.epmedu.animeal.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Outlined.More: ImageVector
    get() {
        if (_More != null) {
            return _More!!
        }
        _More = ImageVector.Builder(
            name = "Outlined.More",
            defaultWidth = 26.dp,
            defaultHeight = 26.dp,
            viewportWidth = 26f,
            viewportHeight = 26f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF00283D)),
                strokeLineWidth = 2f
            ) {
                moveTo(5f, 8f)
                curveTo(5f, 6.343f, 6.343f, 5f, 8f, 5f)
                curveTo(9.657f, 5f, 11f, 6.343f, 11f, 8f)
                curveTo(11f, 9.657f, 9.657f, 11f, 8f, 11f)
                curveTo(6.343f, 11f, 5f, 9.657f, 5f, 8f)
                close()
            }
            path(
                stroke = SolidColor(Color(0xFF00283D)),
                strokeLineWidth = 2f
            ) {
                moveTo(5f, 18f)
                curveTo(5f, 16.343f, 6.343f, 15f, 8f, 15f)
                curveTo(9.657f, 15f, 11f, 16.343f, 11f, 18f)
                curveTo(11f, 19.657f, 9.657f, 21f, 8f, 21f)
                curveTo(6.343f, 21f, 5f, 19.657f, 5f, 18f)
                close()
            }
            path(
                stroke = SolidColor(Color(0xFF00283D)),
                strokeLineWidth = 2f
            ) {
                moveTo(15f, 8f)
                curveTo(15f, 6.343f, 16.343f, 5f, 18f, 5f)
                curveTo(19.657f, 5f, 21f, 6.343f, 21f, 8f)
                curveTo(21f, 9.657f, 19.657f, 11f, 18f, 11f)
                curveTo(16.343f, 11f, 15f, 9.657f, 15f, 8f)
                close()
            }
            path(
                stroke = SolidColor(Color(0xFF00283D)),
                strokeLineWidth = 2f
            ) {
                moveTo(15f, 18f)
                curveTo(15f, 16.343f, 16.343f, 15f, 18f, 15f)
                curveTo(19.657f, 15f, 21f, 16.343f, 21f, 18f)
                curveTo(21f, 19.657f, 19.657f, 21f, 18f, 21f)
                curveTo(16.343f, 21f, 15f, 19.657f, 15f, 18f)
                close()
            }
        }.build()

        return _More!!
    }

@Suppress("ObjectPropertyName")
private var _More: ImageVector? = null
