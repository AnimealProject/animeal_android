package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.FaceUpset: ImageVector
    get() {
        if (_FaceUpset != null) {
            return _FaceUpset!!
        }
        _FaceUpset = ImageVector.Builder(
            name = "Colored.FaceUpset",
            defaultWidth = 14.dp,
            defaultHeight = 15.dp,
            viewportWidth = 14f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFF64E53)),
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
                fill = SolidColor(Color(0xFFFF4E63)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(5.05f, 10.595f)
                curveTo(5.294f, 10.597f, 5.528f, 10.493f, 5.691f, 10.31f)
                curveTo(6.022f, 9.934f, 6.498f, 9.719f, 6.999f, 9.719f)
                curveTo(7.499f, 9.719f, 7.976f, 9.934f, 8.307f, 10.31f)
                curveTo(8.47f, 10.493f, 8.704f, 10.597f, 8.949f, 10.595f)
                curveTo(9.299f, 10.599f, 9.618f, 10.393f, 9.759f, 10.072f)
                curveTo(9.9f, 9.751f, 9.837f, 9.377f, 9.598f, 9.121f)
                curveTo(8.934f, 8.385f, 7.99f, 7.966f, 7f, 7.966f)
                curveTo(6.01f, 7.966f, 5.066f, 8.385f, 4.402f, 9.121f)
                curveTo(4.163f, 9.377f, 4.1f, 9.751f, 4.241f, 10.072f)
                curveTo(4.382f, 10.393f, 4.7f, 10.599f, 5.05f, 10.595f)
                close()
            }
        }.build()

        return _FaceUpset!!
    }

@Suppress("ObjectPropertyName")
private var _FaceUpset: ImageVector? = null
