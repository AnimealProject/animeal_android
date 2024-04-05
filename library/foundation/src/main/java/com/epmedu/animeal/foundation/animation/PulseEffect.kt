package com.epmedu.animeal.foundation.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun PulseEffect(
    infiniteTransition: InfiniteTransition,
    durationMillis: Int,
    minSize: Dp,
    maxSize: Dp,
    shape: Shape = CircleShape,
    delayMillis: Int = 0,
    easing: Easing = LinearEasing,
    initialStartOffset: StartOffset = StartOffset(0),
    colors: Pair<Color, Color> = Color.White to Color(0x00FFFFFF)
) {
    val pulseSize by infiniteTransition.animateValue(
        initialValue = minSize,
        targetValue = maxSize,
        typeConverter = getDpTypeConverter(),
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis, delayMillis, easing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = initialStartOffset
        ),
        label = "pulse size animation"
    )

    val pulseColor by infiniteTransition.animateColor(
        initialValue = colors.first,
        targetValue = colors.second,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis, delayMillis, easing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = initialStartOffset
        ),
        label = "pulse color animation"
    )

    Box(
        modifier = Modifier
            .background(
                color = pulseColor,
                shape = shape
            )
            .size(pulseSize)
    )
}

@AnimealPreview
@Composable
private fun PulseEffectPreview() {
    AnimealTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Gray
        ) {
            Box {
                PulseEffect(
                    infiniteTransition = rememberInfiniteTransition(label = ""),
                    durationMillis = 500,
                    delayMillis = 1500,
                    minSize = 52.dp,
                    maxSize = 90.dp,
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Black,
                            shape = CircleShape
                        )
                        .size(52.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}