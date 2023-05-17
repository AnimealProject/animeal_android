package com.epmedu.animeal.foundation.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.epmedu.animeal.foundation.theme.Shapes

private const val LEADING_OFFSET = -2000f
private const val TRAILING_OFFSET = 2000f
private const val SHIMMER_ANIMATION_DURATION = 1500
private const val SHIMMER_SIZE = 1248

@Composable
fun ShimmerLoading(
    modifier: Modifier = Modifier,
    shape: Shape = Shapes.large,
    alpha: Float = 1.0f
) {
    Box(modifier = modifier.background(gradient(), shape, alpha))
}

@Composable
private fun gradient(): Brush {
    val infiniteTransition = rememberInfiniteTransition()

    val startX by infiniteTransition.animateFloat(
        initialValue = LEADING_OFFSET,
        targetValue = TRAILING_OFFSET,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = SHIMMER_ANIMATION_DURATION,
                easing = LinearEasing
            )
        )
    )

    val colorStops = if (isSystemInDarkTheme()) {
        listOf(
            0.15f to Color.DarkGray,
            0.4f to Color.Gray,
            0.85f to Color.DarkGray
        )
    } else {
        listOf(
            0.15f to Color.LightGray,
            0.4f to Color.White,
            0.85f to Color.LightGray
        )
    }

    return Brush.horizontalGradient(
        colorStops = colorStops.toTypedArray(),
        startX = startX,
        endX = SHIMMER_SIZE + startX
    )
}