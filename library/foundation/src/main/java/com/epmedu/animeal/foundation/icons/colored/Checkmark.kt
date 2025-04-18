package com.epmedu.animeal.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons

val AnimealIcons.Colored.Checkmark: ImageVector
    get() {
        if (_Checkmark != null) {
            return _Checkmark!!
        }
        _Checkmark = ImageVector.Builder(
            name = "Colored.Checkmark",
            defaultWidth = 20.dp,
            defaultHeight = 20.dp,
            viewportWidth = 20f,
            viewportHeight = 20f
        ).apply {
            path(fill = SolidColor(Color(0xFFFFFFFF))) {
                moveTo(19.127f, 0.153f)
                curveTo(18.224f, -0.397f, 17.218f, 0.664f, 16.627f, 1.293f)
                curveTo(15.274f, 2.786f, 14.128f, 4.515f, 12.844f, 6.087f)
                curveTo(11.421f, 7.816f, 10.102f, 9.545f, 8.644f, 11.234f)
                curveTo(7.811f, 12.178f, 6.908f, 13.199f, 6.353f, 14.378f)
                curveTo(5.103f, 13.003f, 4.027f, 11.509f, 2.639f, 10.291f)
                curveTo(1.632f, 9.427f, -0.034f, 8.798f, 0.001f, 10.881f)
                curveTo(0.07f, 13.592f, 2.187f, 16.5f, 3.749f, 18.347f)
                curveTo(4.409f, 19.133f, 5.277f, 19.958f, 6.283f, 19.997f)
                curveTo(7.498f, 20.076f, 8.748f, 18.426f, 9.477f, 17.522f)
                curveTo(10.761f, 15.95f, 11.802f, 14.182f, 12.983f, 12.571f)
                curveTo(14.51f, 10.449f, 16.072f, 8.366f, 17.565f, 6.205f)
                curveTo(18.502f, 4.869f, 21.452f, 1.568f, 19.127f, 0.153f)
                close()
            }
        }.build()

        return _Checkmark!!
    }

@Suppress("ObjectPropertyName")
private var _Checkmark: ImageVector? = null
