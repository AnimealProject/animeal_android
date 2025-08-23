package com.epmedu.animeal.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Outlined.Geolocation: ImageVector
    get() {
        if (_Geolocation != null) {
            return _Geolocation!!
        }
        _Geolocation = ImageVector.Builder(
            name = "Outlined.Geolocation",
            defaultWidth = 26.dp,
            defaultHeight = 26.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(12f, 18f)
                curveTo(15.314f, 18f, 18f, 15.314f, 18f, 12f)
                curveTo(18f, 8.686f, 15.314f, 6f, 12f, 6f)
                curveTo(8.686f, 6f, 6f, 8.686f, 6f, 12f)
                curveTo(6f, 15.314f, 8.686f, 18f, 12f, 18f)
                close()
                moveTo(19.938f, 13f)
                curveTo(19.487f, 16.619f, 16.619f, 19.487f, 13f, 19.938f)
                verticalLineTo(22f)
                curveTo(13f, 22.552f, 12.552f, 23f, 12f, 23f)
                curveTo(11.448f, 23f, 11f, 22.552f, 11f, 22f)
                verticalLineTo(19.938f)
                curveTo(7.381f, 19.487f, 4.513f, 16.619f, 4.062f, 13f)
                horizontalLineTo(2f)
                curveTo(1.448f, 13f, 1f, 12.552f, 1f, 12f)
                curveTo(1f, 11.448f, 1.448f, 11f, 2f, 11f)
                horizontalLineTo(4.062f)
                curveTo(4.513f, 7.381f, 7.381f, 4.513f, 11f, 4.062f)
                verticalLineTo(2f)
                curveTo(11f, 1.448f, 11.448f, 1f, 12f, 1f)
                curveTo(12.552f, 1f, 13f, 1.448f, 13f, 2f)
                verticalLineTo(4.062f)
                curveTo(16.619f, 4.513f, 19.487f, 7.381f, 19.938f, 11f)
                horizontalLineTo(22f)
                curveTo(22.552f, 11f, 23f, 11.448f, 23f, 12f)
                curveTo(23f, 12.552f, 22.552f, 13f, 22f, 13f)
                horizontalLineTo(19.938f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(12f, 9.6f)
                curveTo(10.675f, 9.6f, 9.6f, 10.677f, 9.6f, 12.005f)
                curveTo(9.6f, 13.334f, 10.675f, 14.411f, 12f, 14.411f)
                curveTo(13.326f, 14.411f, 14.401f, 13.334f, 14.401f, 12.005f)
                curveTo(14.401f, 10.677f, 13.326f, 9.6f, 12f, 9.6f)
                close()
            }
        }.build()

        return _Geolocation!!
    }

@Suppress("ObjectPropertyName")
private var _Geolocation: ImageVector? = null
