package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.FaceNeutral: ImageVector
    get() {
        if (_FaceNeutral != null) {
            return _FaceNeutral!!
        }
        _FaceNeutral = ImageVector.Builder(
            name = "Colored.FaceNeutral",
            defaultWidth = 14.dp,
            defaultHeight = 15.dp,
            viewportWidth = 14f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFF1A000)),
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
                fill = SolidColor(Color(0xFFF1A000)),
                stroke = SolidColor(Color(0xFFF1A000)),
                strokeLineWidth = 1f
            ) {
                moveTo(4.75f, 9.711f)
                curveTo(4.612f, 9.711f, 4.5f, 9.599f, 4.5f, 9.461f)
                curveTo(4.5f, 9.322f, 4.612f, 9.211f, 4.75f, 9.211f)
                horizontalLineTo(9.25f)
                curveTo(9.388f, 9.211f, 9.5f, 9.322f, 9.5f, 9.461f)
                curveTo(9.5f, 9.599f, 9.388f, 9.711f, 9.25f, 9.711f)
                horizontalLineTo(4.75f)
                close()
            }
        }.build()

        return _FaceNeutral!!
    }

@Suppress("ObjectPropertyName")
private var _FaceNeutral: ImageVector? = null
