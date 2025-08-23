package com.epmedu.animeal.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Outlined.Search: ImageVector
    get() {
        if (_Search != null) {
            return _Search!!
        }
        _Search = ImageVector.Builder(
            name = "Outlined.Search",
            defaultWidth = 26.dp,
            defaultHeight = 26.dp,
            viewportWidth = 26f,
            viewportHeight = 26f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(6.61f, 11.736f)
                curveTo(6.61f, 8.671f, 9.102f, 6.16f, 12.186f, 6.16f)
                curveTo(15.269f, 6.16f, 17.743f, 8.671f, 17.743f, 11.736f)
                curveTo(17.743f, 14.8f, 15.25f, 17.311f, 12.186f, 17.311f)
                curveTo(9.121f, 17.311f, 6.61f, 14.819f, 6.61f, 11.736f)
                close()
                moveTo(21.213f, 20.154f)
                lineTo(17.946f, 16.886f)
                curveTo(19.164f, 15.52f, 19.921f, 13.711f, 19.921f, 11.736f)
                curveTo(19.921f, 7.471f, 16.45f, 4f, 12.186f, 4f)
                curveTo(7.921f, 4f, 4.45f, 7.471f, 4.45f, 11.736f)
                curveTo(4.45f, 16f, 7.921f, 19.471f, 12.186f, 19.471f)
                curveTo(13.699f, 19.471f, 15.102f, 19.028f, 16.284f, 18.289f)
                lineTo(19.681f, 21.686f)
                curveTo(19.884f, 21.889f, 20.161f, 22f, 20.438f, 22f)
                curveTo(20.715f, 22f, 20.992f, 21.889f, 21.195f, 21.686f)
                curveTo(21.638f, 21.262f, 21.638f, 20.579f, 21.213f, 20.154f)
                horizontalLineTo(21.213f)
                close()
            }
        }.build()

        return _Search!!
    }

@Suppress("ObjectPropertyName")
private var _Search: ImageVector? = null
