package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.FaceHappy: ImageVector
    get() {
        if (_FaceHappy != null) {
            return _FaceHappy!!
        }
        _FaceHappy = ImageVector.Builder(
            name = "Colored.FaceHappy",
            defaultWidth = 14.dp,
            defaultHeight = 15.dp,
            viewportWidth = 14f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF3BC372)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(9.625f, 5.344f)
                curveTo(9.142f, 5.344f, 8.75f, 5.735f, 8.75f, 6.219f)
                curveTo(8.75f, 6.702f, 9.142f, 7.094f, 9.625f, 7.094f)
                curveTo(10.108f, 7.094f, 10.5f, 6.702f, 10.5f, 6.219f)
                curveTo(10.5f, 5.735f, 10.108f, 5.344f, 9.625f, 5.344f)
                close()
                moveTo(5.25f, 6.219f)
                curveTo(5.25f, 5.735f, 4.858f, 5.344f, 4.375f, 5.344f)
                curveTo(3.892f, 5.344f, 3.5f, 5.735f, 3.5f, 6.219f)
                curveTo(3.5f, 6.702f, 3.892f, 7.094f, 4.375f, 7.094f)
                curveTo(4.858f, 7.094f, 5.25f, 6.702f, 5.25f, 6.219f)
                close()
                moveTo(7f, 12.344f)
                curveTo(4.101f, 12.344f, 1.75f, 9.993f, 1.75f, 7.094f)
                curveTo(1.75f, 4.194f, 4.101f, 1.844f, 7f, 1.844f)
                curveTo(9.899f, 1.844f, 12.25f, 4.194f, 12.25f, 7.094f)
                curveTo(12.25f, 9.993f, 9.899f, 12.344f, 7f, 12.344f)
                close()
                moveTo(7f, 0.094f)
                curveTo(3.134f, 0.094f, 0f, 3.228f, 0f, 7.094f)
                curveTo(0f, 10.96f, 3.134f, 14.094f, 7f, 14.094f)
                curveTo(10.866f, 14.094f, 14f, 10.96f, 14f, 7.094f)
                curveTo(14f, 3.228f, 10.866f, 0.094f, 7f, 0.094f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF3BC372)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(8.949f, 7.967f)
                curveTo(8.705f, 7.965f, 8.471f, 8.069f, 8.309f, 8.252f)
                curveTo(7.977f, 8.628f, 7.501f, 8.843f, 7f, 8.843f)
                curveTo(6.5f, 8.843f, 6.024f, 8.628f, 5.693f, 8.252f)
                curveTo(5.529f, 8.069f, 5.295f, 7.965f, 5.05f, 7.967f)
                curveTo(4.7f, 7.963f, 4.381f, 8.169f, 4.24f, 8.49f)
                curveTo(4.099f, 8.811f, 4.162f, 9.185f, 4.401f, 9.441f)
                curveTo(5.065f, 10.177f, 6.009f, 10.596f, 6.999f, 10.596f)
                curveTo(7.989f, 10.596f, 8.933f, 10.177f, 9.597f, 9.441f)
                curveTo(9.836f, 9.185f, 9.899f, 8.811f, 9.758f, 8.49f)
                curveTo(9.617f, 8.169f, 9.299f, 7.963f, 8.949f, 7.967f)
                horizontalLineTo(8.949f)
                close()
            }
        }.build()

        return _FaceHappy!!
    }

@Suppress("ObjectPropertyName")
private var _FaceHappy: ImageVector? = null
