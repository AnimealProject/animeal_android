package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.AnimealLogo: ImageVector
    get() {
        if (_AnimealLogo != null) {
            return _AnimealLogo!!
        }
        _AnimealLogo = ImageVector.Builder(
            name = "Colored.AnimealLogo",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 110f,
            viewportHeight = 110f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF03BFD7)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(27f, 110f)
                curveTo(12.09f, 110f, 0f, 97.91f, 0f, 83f)
                verticalLineTo(27f)
                curveTo(0f, 12.09f, 12.09f, 0f, 27f, 0f)
                horizontalLineToRelative(56f)
                curveToRelative(14.91f, 0f, 27f, 12.09f, 27f, 27f)
                verticalLineToRelative(56f)
                curveToRelative(0f, 14.91f, -12.09f, 27f, -27f, 27f)
                horizontalLineTo(27f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(66.17f, 56.76f)
                arcToRelative(16.06f, 16.06f, 0f, isMoreThanHalf = false, isPositiveArc = false, -13.58f, -7.43f)
                arcToRelative(16.06f, 16.06f, 0f, isMoreThanHalf = false, isPositiveArc = false, -13.58f, 7.42f)
                lineToRelative(-7.18f, 11.2f)
                arcToRelative(10.14f, 10.14f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.59f, 6.21f)
                curveToRelative(0.15f, 2.2f, 0.99f, 4.26f, 2.42f, 5.93f)
                arcToRelative(10.14f, 10.14f, 0f, isMoreThanHalf = false, isPositiveArc = false, 5.49f, 3.32f)
                curveToRelative(2.15f, 0.49f, 4.36f, 0.28f, 6.38f, -0.61f)
                lineToRelative(0.14f, -0.06f)
                arcToRelative(20.06f, 20.06f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15.98f, 0.06f)
                arcToRelative(10.15f, 10.15f, 0f, isMoreThanHalf = false, isPositiveArc = false, 6.38f, 0.61f)
                arcToRelative(10.14f, 10.14f, 0f, isMoreThanHalf = false, isPositiveArc = false, 5.49f, -3.32f)
                arcToRelative(10.14f, 10.14f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.42f, -5.93f)
                arcToRelative(10.14f, 10.14f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.59f, -6.21f)
                lineToRelative(-7.18f, -11.2f)
                close()
                moveTo(33.77f, 56.74f)
                curveToRelative(2.14f, -0.82f, 3.76f, -2.54f, 4.57f, -4.85f)
                curveToRelative(0.77f, -2.19f, 0.71f, -4.67f, -0.18f, -6.97f)
                curveToRelative(-0.89f, -2.3f, -2.5f, -4.18f, -4.55f, -5.29f)
                curveToRelative(-2.15f, -1.17f, -4.5f, -1.36f, -6.64f, -0.54f)
                curveToRelative(-4.29f, 1.65f, -6.26f, 6.95f, -4.39f, 11.82f)
                curveToRelative(1.49f, 3.88f, 4.98f, 6.32f, 8.5f, 6.32f)
                curveToRelative(0.9f, 0f, 1.8f, -0.16f, 2.67f, -0.5f)
                close()
                moveTo(47.71f, 47.97f)
                curveToRelative(5.36f, 0f, 9.73f, -4.97f, 9.73f, -11.08f)
                curveToRelative(0f, -6.11f, -4.36f, -11.09f, -9.73f, -11.09f)
                curveToRelative(-5.36f, 0f, -9.73f, 4.97f, -9.73f, 11.09f)
                curveToRelative(0f, 6.11f, 4.36f, 11.08f, 9.73f, 11.08f)
                close()
                moveTo(64.51f, 50.69f)
                arcToRelative(7.93f, 7.93f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.51f, 0.41f)
                curveToRelative(3.91f, 0f, 7.72f, -2.85f, 9.18f, -7.22f)
                curveToRelative(0.84f, -2.52f, 0.78f, -5.18f, -0.16f, -7.5f)
                curveToRelative(-0.98f, -2.42f, -2.8f, -4.18f, -5.12f, -4.96f)
                curveToRelative(-2.32f, -0.77f, -4.83f, -0.45f, -7.07f, 0.91f)
                curveToRelative(-2.14f, 1.29f, -3.78f, 3.39f, -4.61f, 5.91f)
                curveToRelative(-1.77f, 5.31f, 0.6f, 10.9f, 5.27f, 12.45f)
                close()
                moveTo(84.99f, 49.4f)
                lineTo(84.99f, 49.4f)
                curveToRelative(-3.7f, -2.73f, -9.23f, -1.55f, -12.33f, 2.64f)
                curveToRelative(-3.1f, 4.19f, -2.61f, 9.83f, 1.08f, 12.56f)
                curveToRelative(1.35f, 1f, 2.94f, 1.48f, 4.57f, 1.48f)
                curveToRelative(2.84f, 0f, 5.79f, -1.45f, 7.77f, -4.11f)
                curveToRelative(3.1f, -4.19f, 2.61f, -9.83f, -1.08f, -12.56f)
                close()
            }
        }.build()

        return _AnimealLogo!!
    }

@Suppress("ObjectPropertyName")
private var _AnimealLogo: ImageVector? = null
