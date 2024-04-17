package com.epmedu.animeal.foundation.animation

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun DecreasingEffect(
    infiniteTransition: InfiniteTransition,
    animationDurationInMillis: Int,
    easing: Easing,
    initialSize: Dp,
    targetSize: Dp,
    content: @Composable (modifier: Modifier) -> Unit
) {
    val size by infiniteTransition.animateValue(
        initialValue = initialSize,
        targetValue = targetSize,
        typeConverter = getDpTypeConverter(),
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = animationDurationInMillis,
                easing = easing
            )
        ),
        label = "decreasing effect animation"
    )

    content(Modifier.size(size))
}