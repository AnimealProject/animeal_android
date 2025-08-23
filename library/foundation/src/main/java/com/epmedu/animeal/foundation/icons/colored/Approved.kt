package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.Approved: ImageVector
    get() {
        if (_Approved != null) {
            return _Approved!!
        }
        _Approved = ImageVector.Builder(
            name = "Colored.Approved",
            defaultWidth = 14.dp,
            defaultHeight = 15.dp,
            viewportWidth = 14f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF3BC372)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(9.73798f, 4.04596f)
                curveTo(9.46723f, 3.88092f, 9.16524f, 4.19921f, 8.98821f, 4.38783f)
                curveTo(8.5821f, 4.83579f, 8.23846f, 5.35448f, 7.85314f, 5.82603f)
                curveTo(7.42619f, 6.34472f, 7.03048f, 6.86342f, 6.59311f, 7.37034f)
                curveTo(6.34319f, 7.65327f, 6.07244f, 7.95977f, 5.90583f, 8.31343f)
                curveTo(5.53094f, 7.90081f, 5.20812f, 7.45284f, 4.79158f, 7.08742f)
                curveTo(4.48959f, 6.82807f, 3.98975f, 6.63945f, 4.00016f, 7.26425f)
                curveTo(4.02099f, 8.07768f, 4.65621f, 8.95003f, 5.12481f, 9.50407f)
                curveTo(5.32267f, 9.73984f, 5.58301f, 9.9874f, 5.885f, 9.99919f)
                curveTo(6.24947f, 10.0228f, 6.62435f, 9.52765f, 6.84304f, 9.25651f)
                curveTo(7.22835f, 8.78497f, 7.54076f, 8.25446f, 7.8948f, 7.77115f)
                curveTo(8.35299f, 7.13457f, 8.8216f, 6.50976f, 9.26938f, 5.86139f)
                curveTo(9.55054f, 5.46058f, 10.4357f, 4.47033f, 9.73798f, 4.04596f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF3BC372)),
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
        }.build()

        return _Approved!!
    }

@Suppress("ObjectPropertyName")
private var _Approved: ImageVector? = null
