package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.PendingRed: ImageVector
    get() {
        if (_PendingRed != null) {
            return _PendingRed!!
        }
        _PendingRed = ImageVector.Builder(
            name = "Colored.PendingRed",
            defaultWidth = 14.dp,
            defaultHeight = 15.dp,
            viewportWidth = 14f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFF64E53)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(10.1202f, 6.003f)
                curveTo(9.63746f, 6.003f, 9.24609f, 6.39475f, 9.24609f, 6.878f)
                curveTo(9.24609f, 7.36125f, 9.63746f, 7.753f, 10.1202f, 7.753f)
                curveTo(10.603f, 7.753f, 10.9944f, 7.36125f, 10.9944f, 6.878f)
                curveTo(10.9944f, 6.39475f, 10.603f, 6.003f, 10.1202f, 6.003f)
                close()
                moveTo(6.87413f, 6.003f)
                curveTo(6.39136f, 6.003f, 6f, 6.39475f, 6f, 6.878f)
                curveTo(6f, 7.36125f, 6.39136f, 7.753f, 6.87413f, 7.753f)
                curveTo(7.35691f, 7.753f, 7.74827f, 7.36125f, 7.74827f, 6.878f)
                curveTo(7.74827f, 6.39475f, 7.35691f, 6.003f, 6.87413f, 6.003f)
                close()
                moveTo(4.74827f, 6.878f)
                curveTo(4.74827f, 6.39475f, 4.35691f, 6.003f, 3.87413f, 6.003f)
                curveTo(3.39136f, 6.003f, 3f, 6.39475f, 3f, 6.878f)
                curveTo(3f, 7.36125f, 3.39136f, 7.753f, 3.87413f, 7.753f)
                curveTo(4.35691f, 7.753f, 4.74827f, 7.36125f, 4.74827f, 6.878f)
                close()
                moveTo(6.99329f, 12.2501f)
                curveTo(4.09667f, 12.2501f, 1.74848f, 9.89958f, 1.74848f, 7.00007f)
                curveTo(1.74848f, 4.10056f, 4.09667f, 1.75007f, 6.99329f, 1.75007f)
                curveTo(9.88991f, 1.75007f, 12.2381f, 4.10056f, 12.2381f, 7.00007f)
                curveTo(12.2381f, 9.89958f, 9.88991f, 12.2501f, 6.99329f, 12.2501f)
                close()
                moveTo(6.99307f, 0f)
                curveTo(3.1309f, 0f, 0f, 3.134f, 0f, 7f)
                curveTo(0f, 10.866f, 3.13091f, 14f, 6.99307f, 14f)
                curveTo(10.8552f, 14f, 13.9861f, 10.866f, 13.9861f, 7f)
                curveTo(13.9861f, 3.134f, 10.8552f, 0f, 6.99307f, 0f)
                close()
            }
        }.build()

        return _PendingRed!!
    }

@Suppress("ObjectPropertyName")
private var _PendingRed: ImageVector? = null
