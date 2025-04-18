package com.epmedu.animeal.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Outlined.Linkedin: ImageVector
    get() {
        if (_Linkedin != null) {
            return _Linkedin!!
        }
        _Linkedin = ImageVector.Builder(
            name = "Outlined.Linkedin",
            defaultWidth = 19.dp,
            defaultHeight = 19.dp,
            viewportWidth = 19f,
            viewportHeight = 19f
        ).apply {
            path(fill = SolidColor(Color(0xFF00283D))) {
                moveTo(18.878f, 11.061f)
                verticalLineTo(18.039f)
                horizontalLineTo(14.83f)
                verticalLineTo(11.525f)
                curveTo(14.83f, 9.887f, 14.247f, 8.774f, 12.782f, 8.774f)
                curveTo(11.662f, 8.774f, 11.001f, 9.528f, 10.706f, 10.253f)
                curveTo(10.6f, 10.514f, 10.568f, 10.88f, 10.568f, 11.241f)
                verticalLineTo(18.038f)
                horizontalLineTo(6.524f)
                curveTo(6.524f, 18.038f, 6.578f, 7.01f, 6.524f, 5.868f)
                horizontalLineTo(10.569f)
                verticalLineTo(7.593f)
                curveTo(10.563f, 7.607f, 10.551f, 7.619f, 10.546f, 7.635f)
                horizontalLineTo(10.569f)
                verticalLineTo(7.593f)
                curveTo(11.109f, 6.764f, 12.066f, 5.582f, 14.216f, 5.582f)
                curveTo(16.884f, 5.582f, 18.878f, 7.322f, 18.878f, 11.061f)
                close()
                moveTo(2.29f, 0f)
                curveTo(0.906f, 0f, 0f, 0.908f, 0f, 2.105f)
                curveTo(0f, 3.27f, 0.879f, 4.208f, 2.238f, 4.208f)
                horizontalLineTo(2.262f)
                curveTo(3.676f, 4.208f, 4.553f, 3.27f, 4.553f, 2.105f)
                curveTo(4.525f, 0.908f, 3.677f, 0f, 2.29f, 0f)
                close()
                moveTo(0.242f, 18.039f)
                horizontalLineTo(4.287f)
                verticalLineTo(5.867f)
                horizontalLineTo(0.242f)
                verticalLineTo(18.039f)
                close()
            }
        }.build()

        return _Linkedin!!
    }

@Suppress("ObjectPropertyName")
private var _Linkedin: ImageVector? = null
