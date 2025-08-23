package com.epmedu.animeal.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Outlined.Favorites: ImageVector
    get() {
        if (_Favorites != null) {
            return _Favorites!!
        }
        _Favorites = ImageVector.Builder(
            name = "Outlined.Favorites",
            defaultWidth = 26.dp,
            defaultHeight = 26.dp,
            viewportWidth = 26f,
            viewportHeight = 26f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00283D)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(16.674f, 4.431f)
                curveTo(15.346f, 4.429f, 14.054f, 4.86f, 12.993f, 5.658f)
                curveTo(10.476f, 3.788f, 6.954f, 4.116f, 4.827f, 6.42f)
                curveTo(2.699f, 8.724f, 2.651f, 12.261f, 4.716f, 14.621f)
                curveTo(5.405f, 15.411f, 9.944f, 19.661f, 11.318f, 20.945f)
                curveTo(12.261f, 21.827f, 13.727f, 21.827f, 14.67f, 20.945f)
                curveTo(16.039f, 19.663f, 20.559f, 15.424f, 21.257f, 14.634f)
                curveTo(22.858f, 12.829f, 23.25f, 10.253f, 22.262f, 8.052f)
                curveTo(21.273f, 5.851f, 19.086f, 4.434f, 16.674f, 4.431f)
                horizontalLineTo(16.674f)
                close()
                moveTo(16.674f, 6.885f)
                curveTo(18.121f, 6.888f, 19.433f, 7.738f, 20.025f, 9.059f)
                curveTo(20.618f, 10.379f, 20.382f, 11.924f, 19.422f, 13.008f)
                curveTo(18.747f, 13.768f, 12.993f, 19.154f, 12.993f, 19.154f)
                curveTo(12.993f, 19.154f, 7.226f, 13.768f, 6.551f, 12.995f)
                curveTo(5.956f, 12.326f, 5.629f, 11.461f, 5.631f, 10.566f)
                curveTo(5.631f, 8.533f, 7.279f, 6.885f, 9.312f, 6.885f)
                curveTo(11.345f, 6.885f, 12.993f, 8.533f, 12.993f, 10.566f)
                curveTo(12.993f, 8.533f, 14.641f, 6.885f, 16.674f, 6.885f)
            }
        }.build()

        return _Favorites!!
    }

@Suppress("ObjectPropertyName")
private var _Favorites: ImageVector? = null
