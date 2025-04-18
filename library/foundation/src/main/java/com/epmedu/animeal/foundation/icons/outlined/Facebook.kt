package com.epmedu.animeal.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Outlined.Facebook: ImageVector
    get() {
        if (_Facebook != null) {
            return _Facebook!!
        }
        _Facebook = ImageVector.Builder(
            name = "Outlined.Facebook",
            defaultWidth = 13.2.dp,
            defaultHeight = 24.dp,
            viewportWidth = 11f,
            viewportHeight = 20f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(7.412f, 11.5f)
                horizontalLineToRelative(2.493f)
                lineToRelative(0.997f, -4f)
                horizontalLineToRelative(-3.49f)
                verticalLineToRelative(-2f)
                curveToRelative(0f, -1.03f, 0f, -2f, 1.995f, -2f)
                horizontalLineToRelative(1.495f)
                verticalLineTo(0.14f)
                curveTo(10.577f, 0.097f, 9.35f, 0f, 8.053f, 0f)
                curveToRelative(-2.707f, 0f, -4.63f, 1.657f, -4.63f, 4.7f)
                verticalLineToRelative(2.8f)
                horizontalLineTo(0.432f)
                verticalLineToRelative(4f)
                horizontalLineToRelative(2.991f)
                verticalLineTo(20f)
                horizontalLineToRelative(3.99f)
                verticalLineToRelative(-8.5f)
                close()
            }
        }.build()

        return _Facebook!!
    }

@Suppress("ObjectPropertyName")
private var _Facebook: ImageVector? = null
