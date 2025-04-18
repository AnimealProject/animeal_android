package com.epmedu.animeal.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Outlined.Home: ImageVector
    get() {
        if (_Home != null) {
            return _Home!!
        }
        _Home = ImageVector.Builder(
            name = "Outlined.Home",
            defaultWidth = 24.dp,
            defaultHeight = 23.dp,
            viewportWidth = 24f,
            viewportHeight = 23f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00283D)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(23.544f, 10.269f)
                lineTo(12.846f, 0.521f)
                curveTo(12.377f, 0.093f, 11.668f, 0.093f, 11.199f, 0.521f)
                lineTo(0.501f, 10.269f)
                curveTo(0.124f, 10.612f, 0f, 11.14f, 0.184f, 11.614f)
                curveTo(0.368f, 12.089f, 0.815f, 12.395f, 1.324f, 12.395f)
                horizontalLineTo(3.033f)
                verticalLineTo(22.162f)
                curveTo(3.033f, 22.549f, 3.347f, 22.863f, 3.734f, 22.863f)
                horizontalLineTo(9.598f)
                curveTo(9.985f, 22.863f, 10.299f, 22.549f, 10.299f, 22.162f)
                verticalLineTo(16.232f)
                horizontalLineTo(13.746f)
                verticalLineTo(22.162f)
                curveTo(13.746f, 22.549f, 14.06f, 22.863f, 14.447f, 22.863f)
                horizontalLineTo(20.311f)
                curveTo(20.698f, 22.863f, 21.012f, 22.549f, 21.012f, 22.162f)
                verticalLineTo(12.395f)
                horizontalLineTo(22.721f)
                curveTo(23.23f, 12.395f, 23.677f, 12.089f, 23.861f, 11.614f)
                curveTo(24.045f, 11.14f, 23.921f, 10.612f, 23.545f, 10.269f)
                horizontalLineTo(23.544f)
                close()
            }
        }.build()

        return _Home!!
    }

@Suppress("ObjectPropertyName")
private var _Home: ImageVector? = null
