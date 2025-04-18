package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.EmptyScreenBone: ImageVector
    get() {
        if (_EmptyScreenBone != null) {
            return _EmptyScreenBone!!
        }
        _EmptyScreenBone = ImageVector.Builder(
            name = "Colored.EmptyScreenBone",
            defaultWidth = 206.dp,
            defaultHeight = 200.dp,
            viewportWidth = 206f,
            viewportHeight = 200f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFF7F7F7)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(5.15f, 143.01f)
                curveTo(5.15f, 143.01f, -13.09f, 107.64f, 19.07f, 58.75f)
                curveTo(51.23f, 9.86f, 76.92f, -2.96f, 106.89f, 0.54f)
                curveTo(136.87f, 4.04f, 148.59f, 24.33f, 165.3f, 59.47f)
                curveTo(182.01f, 94.6f, 218.97f, 100.26f, 201.35f, 143.01f)
                curveTo(183.72f, 185.75f, 147.86f, 204.81f, 96.37f, 198.97f)
                curveTo(20.92f, 189.13f, 5.15f, 143.01f, 5.15f, 143.01f)
                close()
            }
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(119.79f, 87.76f)
                curveTo(116.42f, 91.13f, 110.94f, 91.13f, 107.57f, 87.76f)
                curveTo(104.19f, 84.38f, 104.19f, 78.91f, 107.57f, 75.53f)
                curveTo(110.94f, 72.16f, 116.42f, 72.16f, 119.79f, 75.53f)
                curveTo(123.17f, 78.91f, 123.17f, 84.38f, 119.79f, 87.76f)
                close()
            }
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(129.44f, 97.85f)
                curveTo(126.04f, 101.25f, 120.54f, 101.27f, 117.17f, 97.9f)
                curveTo(113.79f, 94.52f, 113.81f, 89.03f, 117.21f, 85.63f)
                curveTo(120.61f, 82.23f, 126.11f, 82.21f, 129.48f, 85.58f)
                curveTo(132.86f, 88.96f, 132.84f, 94.45f, 129.44f, 97.85f)
                close()
            }
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(82.76f, 124.79f)
                curveTo(79.38f, 128.17f, 73.91f, 128.17f, 70.53f, 124.79f)
                curveTo(67.16f, 121.42f, 67.16f, 115.94f, 70.53f, 112.57f)
                curveTo(73.91f, 109.19f, 79.38f, 109.19f, 82.76f, 112.57f)
                curveTo(86.13f, 115.94f, 86.13f, 121.42f, 82.76f, 124.79f)
                close()
            }
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(92.63f, 134.66f)
                curveTo(89.25f, 138.04f, 83.78f, 138.04f, 80.4f, 134.66f)
                curveTo(77.03f, 131.29f, 77.03f, 125.81f, 80.4f, 122.44f)
                curveTo(83.78f, 119.06f, 89.25f, 119.06f, 92.63f, 122.44f)
                curveTo(96f, 125.81f, 96f, 131.29f, 92.63f, 134.66f)
                close()
            }
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(113.68f, 81.73f)
                lineTo(123.46f, 91.51f)
                lineTo(86.51f, 128.46f)
                lineTo(76.73f, 118.68f)
                lineTo(113.68f, 81.73f)
                close()
            }
        }.build()

        return _EmptyScreenBone!!
    }

@Suppress("ObjectPropertyName")
private var _EmptyScreenBone: ImageVector? = null
