package com.epmedu.animeal.foundation.animation

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

@Composable
fun BacklightEffect(
    infiniteTransition: InfiniteTransition,
    animationDurationInMillis: Int,
    easing: Easing,
    size: Dp,
    shape: Shape = CircleShape
) {
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.79f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = animationDurationInMillis,
                easing = easing,
            )
        ),
        label = "backlight effect alpha animation"
    )

    Box(
        modifier = Modifier
            .background(
                brush = Brush.radialGradient(
                    0.5f to Color(0xFFF04E45),
                    1f to Color(0x00EFB5B2)
                ),
                shape = shape,
                alpha = alpha
            )
            .size(size)
    )
}