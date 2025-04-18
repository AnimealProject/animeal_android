package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.Georgia: ImageVector
    get() {
        if (_Georgia != null) {
            return _Georgia!!
        }
        _Georgia = ImageVector.Builder(
            name = "Colored.Georgia",
            defaultWidth = 26.dp,
            defaultHeight = 26.dp,
            viewportWidth = 26f,
            viewportHeight = 26f
        ).apply {
            group(
                clipPathData = PathData {
                    moveTo(5f, 0f)
                    lineTo(21f, 0f)
                    arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 26f, 5f)
                    lineTo(26f, 21f)
                    arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 21f, 26f)
                    lineTo(5f, 26f)
                    arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 21f)
                    lineTo(0f, 5f)
                    arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5f, 0f)
                    close()
                }
            ) {
                path(fill = SolidColor(Color(0xFFF5F5F5))) {
                    moveTo(25.974f, 0.038f)
                    horizontalLineTo(0f)
                    verticalLineTo(26.001f)
                    horizontalLineTo(25.974f)
                    verticalLineTo(0.038f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(26f, 10.708f)
                    horizontalLineTo(0f)
                    verticalLineTo(15.077f)
                    horizontalLineTo(26f)
                    verticalLineTo(10.708f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(15f, 0f)
                    horizontalLineTo(11f)
                    verticalLineTo(26f)
                    horizontalLineTo(15f)
                    verticalLineTo(0f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(4.344f, 3f)
                    curveTo(5.169f, 4.669f, 5.354f, 6.425f, 4.344f, 8f)
                    horizontalLineTo(6.65f)
                    curveTo(5.82f, 5.963f, 5.915f, 4.575f, 6.65f, 3f)
                    horizontalLineTo(4.344f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(3f, 6.655f)
                    curveTo(4.669f, 5.829f, 6.425f, 5.644f, 8f, 6.655f)
                    verticalLineTo(4.348f)
                    curveTo(5.963f, 5.179f, 4.575f, 5.084f, 3f, 4.348f)
                    verticalLineTo(6.655f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(4.344f, 18f)
                    curveTo(5.169f, 19.669f, 5.354f, 21.425f, 4.344f, 23f)
                    horizontalLineTo(6.65f)
                    curveTo(5.82f, 20.962f, 5.915f, 19.575f, 6.65f, 18f)
                    horizontalLineTo(4.344f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(3f, 21.655f)
                    curveTo(4.669f, 20.829f, 6.425f, 20.644f, 8f, 21.655f)
                    verticalLineTo(19.348f)
                    curveTo(5.963f, 20.179f, 4.575f, 20.084f, 3f, 19.348f)
                    verticalLineTo(21.655f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(19.344f, 18f)
                    curveTo(20.169f, 19.669f, 20.354f, 21.425f, 19.344f, 23f)
                    horizontalLineTo(21.65f)
                    curveTo(20.82f, 20.962f, 20.915f, 19.575f, 21.65f, 18f)
                    horizontalLineTo(19.344f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(18f, 21.655f)
                    curveTo(19.669f, 20.829f, 21.425f, 20.644f, 23f, 21.655f)
                    verticalLineTo(19.348f)
                    curveTo(20.962f, 20.179f, 19.575f, 20.084f, 18f, 19.348f)
                    verticalLineTo(21.655f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(19.344f, 3f)
                    curveTo(20.169f, 4.669f, 20.354f, 6.425f, 19.344f, 8f)
                    horizontalLineTo(21.65f)
                    curveTo(20.82f, 5.963f, 20.915f, 4.575f, 21.65f, 3f)
                    horizontalLineTo(19.344f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF4B55))) {
                    moveTo(18f, 6.655f)
                    curveTo(19.669f, 5.829f, 21.425f, 5.644f, 23f, 6.655f)
                    verticalLineTo(4.348f)
                    curveTo(20.962f, 5.179f, 19.575f, 5.084f, 18f, 4.348f)
                    verticalLineTo(6.655f)
                    close()
                }
            }
        }.build()

        return _Georgia!!
    }

@Suppress("ObjectPropertyName")
private var _Georgia: ImageVector? = null
