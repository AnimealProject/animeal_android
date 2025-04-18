package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.Picture: ImageVector
    get() {
        if (_Picture != null) {
            return _Picture!!
        }
        _Picture = ImageVector.Builder(
            name = "Colored.Picture",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF03BFD7)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(20.007f, 1.706f)
                horizontalLineTo(3.994f)
                curveTo(2.73f, 1.706f, 1.706f, 2.73f, 1.706f, 3.994f)
                verticalLineTo(20.007f)
                curveTo(1.706f, 21.27f, 2.73f, 22.294f, 3.994f, 22.294f)
                horizontalLineTo(20.007f)
                curveTo(21.27f, 22.294f, 22.294f, 21.27f, 22.294f, 20.007f)
                verticalLineTo(3.994f)
                curveTo(22.294f, 2.73f, 21.27f, 1.706f, 20.007f, 1.706f)
                close()
            }
            path(
                stroke = SolidColor(Color(0xFF03BFD7)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(7.997f, 9.713f)
                curveTo(8.944f, 9.713f, 9.713f, 8.944f, 9.713f, 7.997f)
                curveTo(9.713f, 7.049f, 8.944f, 6.281f, 7.997f, 6.281f)
                curveTo(7.049f, 6.281f, 6.281f, 7.049f, 6.281f, 7.997f)
                curveTo(6.281f, 8.944f, 7.049f, 9.713f, 7.997f, 9.713f)
                close()
            }
            path(
                stroke = SolidColor(Color(0xFF03BFD7)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(22.294f, 15.432f)
                lineTo(16.575f, 9.713f)
                lineTo(3.994f, 22.295f)
            }
        }.build()

        return _Picture!!
    }

@Suppress("ObjectPropertyName")
private var _Picture: ImageVector? = null
