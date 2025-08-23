package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.Person: ImageVector
    get() {
        if (_Person != null) {
            return _Person!!
        }
        _Person = ImageVector.Builder(
            name = "Colored.Person",
            defaultWidth = 17.dp,
            defaultHeight = 17.dp,
            viewportWidth = 17f,
            viewportHeight = 17f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFB7A2C)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(2.586f, 14.5f)
                curveTo(3.556f, 12.084f, 5.897f, 10.5f, 8.501f, 10.5f)
                curveTo(11.104f, 10.5f, 13.446f, 12.084f, 14.415f, 14.5f)
                horizontalLineTo(2.588f)
                horizontalLineTo(2.586f)
                close()
                moveTo(5.36f, 5.5f)
                curveTo(5.389f, 3.929f, 6.609f, 2.637f, 8.176f, 2.52f)
                curveTo(8.392f, 2.493f, 8.61f, 2.493f, 8.825f, 2.52f)
                curveTo(10.409f, 2.763f, 11.524f, 4.203f, 11.363f, 5.797f)
                curveTo(11.202f, 7.391f, 9.82f, 8.579f, 8.221f, 8.5f)
                curveTo(6.621f, 8.42f, 5.363f, 7.102f, 5.36f, 5.5f)
                lineTo(5.36f, 5.5f)
                close()
                moveTo(16.388f, 13.843f)
                curveTo(15.606f, 11.76f, 14f, 10.091f, 11.948f, 9.23f)
                curveTo(13.066f, 8.312f, 13.719f, 6.946f, 13.731f, 5.5f)
                curveTo(13.69f, 2.907f, 11.696f, 0.765f, 9.112f, 0.538f)
                curveTo(8.704f, 0.487f, 8.291f, 0.487f, 7.882f, 0.538f)
                curveTo(5.3f, 0.767f, 3.308f, 2.908f, 3.267f, 5.5f)
                curveTo(3.279f, 6.946f, 3.932f, 8.312f, 5.05f, 9.23f)
                curveTo(2.998f, 10.091f, 1.391f, 11.76f, 0.609f, 13.843f)
                curveTo(0.387f, 14.455f, 0.489f, 15.138f, 0.88f, 15.659f)
                curveTo(1.285f, 16.193f, 1.918f, 16.505f, 2.588f, 16.5f)
                horizontalLineTo(14.417f)
                curveTo(15.087f, 16.504f, 15.719f, 16.193f, 16.123f, 15.659f)
                curveTo(16.513f, 15.137f, 16.613f, 14.454f, 16.388f, 13.843f)
                horizontalLineTo(16.388f)
                close()
            }
        }.build()

        return _Person!!
    }

@Suppress("ObjectPropertyName")
private var _Person: ImageVector? = null
