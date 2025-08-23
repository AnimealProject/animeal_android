package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.AnalysisSoon: ImageVector
    get() {
        if (_AnalysisSoon != null) {
            return _AnalysisSoon!!
        }
        _AnalysisSoon = ImageVector.Builder(
            name = "Colored.AnalysisSoon",
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
            path(
                stroke = SolidColor(Color(0xFF03BFD7)),
                strokeLineWidth = 2f
            ) {
                moveTo(60f, 107.5f)
                lineTo(66f, 101f)
                lineTo(70.5f, 107f)
                lineTo(76f, 95f)
                lineTo(87f, 103f)
                lineTo(90f, 92.5f)
                lineTo(98f, 99f)
                lineTo(101f, 95f)
                lineTo(104.5f, 101f)
                lineTo(110.5f, 90f)
                lineTo(115.5f, 102.5f)
                lineTo(119.5f, 97f)
                lineTo(129f, 105.5f)
            }
            path(
                stroke = SolidColor(Color(0xFF03BFD7)),
                strokeLineWidth = 5f
            ) {
                moveTo(95f, 97f)
                moveToRelative(-15.5f, 0f)
                arcToRelative(15.5f, 15.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, 31f, 0f)
                arcToRelative(15.5f, 15.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, -31f, 0f)
            }
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(109.15f, 123.53f)
                lineTo(114.09f, 120.13f)
                arcTo(2f, 2f, 74.25f, isMoreThanHalf = false, isPositiveArc = true, 116.87f, 120.65f)
                lineTo(126.5f, 134.66f)
                arcTo(2f, 2f, 107.37f, isMoreThanHalf = false, isPositiveArc = true, 125.99f, 137.44f)
                lineTo(121.04f, 140.84f)
                arcTo(2f, 2f, 100.37f, isMoreThanHalf = false, isPositiveArc = true, 118.26f, 140.32f)
                lineTo(108.63f, 126.31f)
                arcTo(2f, 2f, 115.11f, isMoreThanHalf = false, isPositiveArc = true, 109.15f, 123.53f)
                close()
            }
            path(fill = SolidColor(Color(0xFF03BFD7))) {
                moveTo(100.5f, 111.4f)
                lineToRelative(4.94f, -3.4f)
                lineToRelative(12.01f, 17.48f)
                lineToRelative(-4.94f, 3.4f)
                close()
            }
        }.build()

        return _AnalysisSoon!!
    }

@Suppress("ObjectPropertyName")
private var _AnalysisSoon: ImageVector? = null
