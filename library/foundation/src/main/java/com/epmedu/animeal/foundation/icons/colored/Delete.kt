package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.Delete: ImageVector
    get() {
        if (_Delete != null) {
            return _Delete!!
        }
        _Delete = ImageVector.Builder(
            name = "Colored.Delete",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(fill = SolidColor(Color(0xFFFF4B55))) {
                moveTo(6f, 0f)
                lineTo(5f, 0.8f)
                horizontalLineTo(0f)
                verticalLineTo(2.4f)
                horizontalLineTo(1f)
                verticalLineTo(14.4f)
                curveTo(1f, 14.818f, 1.191f, 15.244f, 1.568f, 15.545f)
                curveTo(1.945f, 15.847f, 2.478f, 16f, 3f, 16f)
                horizontalLineTo(13f)
                curveTo(13.522f, 16f, 14.055f, 15.847f, 14.432f, 15.545f)
                curveTo(14.809f, 15.244f, 15f, 14.818f, 15f, 14.4f)
                verticalLineTo(2.4f)
                horizontalLineTo(16f)
                verticalLineTo(0.8f)
                horizontalLineTo(11f)
                lineTo(10f, 0f)
                horizontalLineTo(6f)
                close()
                moveTo(3f, 2.4f)
                horizontalLineTo(13f)
                verticalLineTo(14.4f)
                horizontalLineTo(3f)
                verticalLineTo(2.4f)
                close()
                moveTo(5f, 4f)
                verticalLineTo(12.8f)
                horizontalLineTo(7f)
                verticalLineTo(4f)
                horizontalLineTo(5f)
                close()
                moveTo(9f, 4f)
                verticalLineTo(12.8f)
                horizontalLineTo(11f)
                verticalLineTo(4f)
                horizontalLineTo(9f)
                close()
            }
        }.build()

        return _Delete!!
    }

@Suppress("ObjectPropertyName")
private var _Delete: ImageVector? = null
