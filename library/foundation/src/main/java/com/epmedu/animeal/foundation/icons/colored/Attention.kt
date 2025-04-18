package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.Attention: ImageVector
    get() {
        if (_Attention != null) {
            return _Attention!!
        }
        _Attention = ImageVector.Builder(
            name = "Colored.Attention",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 100f,
            viewportHeight = 100f
        ).apply {
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(93.3f, 24.9f)
                curveTo(88.82f, 17.24f, 82.76f, 11.18f, 75.1f, 6.71f)
                curveTo(67.44f, 2.23f, 59.07f, 0f, 50f, 0f)
                curveTo(40.93f, 0f, 32.56f, 2.23f, 24.9f, 6.71f)
                curveTo(17.24f, 11.18f, 11.18f, 17.24f, 6.71f, 24.9f)
                curveTo(2.23f, 32.56f, 0f, 40.93f, 0f, 50f)
                curveTo(0f, 59.07f, 2.24f, 67.44f, 6.71f, 75.1f)
                curveTo(11.18f, 82.76f, 17.24f, 88.82f, 24.9f, 93.29f)
                curveTo(32.56f, 97.77f, 40.93f, 100f, 50f, 100f)
                curveTo(59.07f, 100f, 67.44f, 97.77f, 75.1f, 93.29f)
                curveTo(82.76f, 88.82f, 88.82f, 82.76f, 93.29f, 75.1f)
                curveTo(97.77f, 67.44f, 100f, 59.07f, 100f, 50f)
                curveTo(100f, 40.93f, 97.77f, 32.56f, 93.3f, 24.9f)
                close()
                moveTo(58.34f, 81.18f)
                curveTo(58.34f, 81.79f, 58.14f, 82.3f, 57.75f, 82.71f)
                curveTo(57.36f, 83.13f, 56.88f, 83.33f, 56.32f, 83.33f)
                horizontalLineTo(43.82f)
                curveTo(43.25f, 83.33f, 42.75f, 83.12f, 42.32f, 82.68f)
                curveTo(41.88f, 82.25f, 41.67f, 81.75f, 41.67f, 81.18f)
                verticalLineTo(68.82f)
                curveTo(41.67f, 68.25f, 41.88f, 67.75f, 42.32f, 67.32f)
                curveTo(42.75f, 66.88f, 43.25f, 66.67f, 43.82f, 66.67f)
                horizontalLineTo(56.32f)
                curveTo(56.88f, 66.67f, 57.36f, 66.87f, 57.75f, 67.28f)
                curveTo(58.14f, 67.7f, 58.34f, 68.21f, 58.34f, 68.82f)
                verticalLineTo(81.18f)
                close()
                moveTo(58.2f, 58.79f)
                curveTo(58.16f, 59.22f, 57.93f, 59.6f, 57.52f, 59.93f)
                curveTo(57.11f, 60.25f, 56.6f, 60.42f, 55.99f, 60.42f)
                horizontalLineTo(43.95f)
                curveTo(43.34f, 60.42f, 42.82f, 60.25f, 42.38f, 59.93f)
                curveTo(41.95f, 59.6f, 41.73f, 59.22f, 41.73f, 58.79f)
                lineTo(40.63f, 18.36f)
                curveTo(40.63f, 17.84f, 40.84f, 17.45f, 41.28f, 17.19f)
                curveTo(41.71f, 16.84f, 42.23f, 16.67f, 42.84f, 16.67f)
                horizontalLineTo(57.16f)
                curveTo(57.77f, 16.67f, 58.29f, 16.84f, 58.73f, 17.19f)
                curveTo(59.16f, 17.45f, 59.38f, 17.84f, 59.38f, 18.36f)
                lineTo(58.2f, 58.79f)
                close()
            }
        }.build()

        return _Attention!!
    }

@Suppress("ObjectPropertyName")
private var _Attention: ImageVector? = null
