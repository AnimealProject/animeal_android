package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.Rejected: ImageVector
    get() {
        if (_Rejected != null) {
            return _Rejected!!
        }
        _Rejected = ImageVector.Builder(
            name = "Colored.Rejected",
            defaultWidth = 14.dp,
            defaultHeight = 15.dp,
            viewportWidth = 14f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFF64E53)),
                pathFillType = PathFillType.EvenOdd
            ) {
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
            path(
                name = "path",
                fill = SolidColor(Color(0xFFF64E53)),
                strokeLineWidth = 1f
            ) {
                moveTo(9.563f, 4.428f)
                lineTo(9.563f, 4.428f)
                curveTo(9.639f, 4.503f, 9.695f, 4.597f, 9.723f, 4.699f)
                curveTo(9.751f, 4.802f, 9.751f, 4.91f, 9.723f, 5.012f)
                curveTo(9.695f, 5.115f, 9.639f, 5.209f, 9.563f, 5.284f)
                lineTo(5.497f, 9.26f)
                curveTo(5.42f, 9.335f, 5.324f, 9.389f, 5.219f, 9.416f)
                curveTo(5.114f, 9.444f, 5.004f, 9.444f, 4.899f, 9.416f)
                curveTo(4.794f, 9.389f, 4.698f, 9.335f, 4.621f, 9.26f)
                lineTo(4.621f, 9.26f)
                curveTo(4.505f, 9.146f, 4.44f, 8.992f, 4.44f, 8.832f)
                curveTo(4.44f, 8.671f, 4.505f, 8.517f, 4.621f, 8.404f)
                lineTo(8.687f, 4.428f)
                curveTo(8.803f, 4.314f, 8.961f, 4.251f, 9.125f, 4.251f)
                curveTo(9.289f, 4.251f, 9.447f, 4.314f, 9.563f, 4.428f)
            }
            path(
                name = "path_1",
                fill = SolidColor(Color(0xFFF64E53)),
                strokeLineWidth = 1f
            ) {
                moveTo(4.438f, 4.44f)
                lineTo(4.438f, 4.44f)
                curveTo(4.514f, 4.365f, 4.61f, 4.311f, 4.715f, 4.284f)
                curveTo(4.82f, 4.256f, 4.93f, 4.256f, 5.035f, 4.284f)
                curveTo(5.14f, 4.311f, 5.236f, 4.365f, 5.313f, 4.44f)
                lineTo(9.435f, 8.471f)
                curveTo(9.512f, 8.546f, 9.567f, 8.64f, 9.595f, 8.742f)
                curveTo(9.623f, 8.845f, 9.623f, 8.953f, 9.595f, 9.056f)
                curveTo(9.567f, 9.158f, 9.512f, 9.252f, 9.435f, 9.327f)
                lineTo(9.435f, 9.327f)
                curveTo(9.358f, 9.402f, 9.262f, 9.456f, 9.158f, 9.484f)
                curveTo(9.053f, 9.511f, 8.942f, 9.511f, 8.837f, 9.484f)
                curveTo(8.732f, 9.456f, 8.637f, 9.402f, 8.56f, 9.327f)
                lineTo(4.438f, 5.296f)
                curveTo(4.322f, 5.183f, 4.256f, 5.029f, 4.256f, 4.868f)
                curveTo(4.256f, 4.708f, 4.322f, 4.554f, 4.438f, 4.44f)
            }
        }.build()

        return _Rejected!!
    }

@Suppress("ObjectPropertyName")
private var _Rejected: ImageVector? = null
