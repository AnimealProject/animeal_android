package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.epmedu.animeal.extensions.SECOND_IN_MILLIS
import com.epmedu.animeal.foundation.animation.BacklightEffect
import com.epmedu.animeal.foundation.animation.DecreasingEffect
import com.epmedu.animeal.foundation.animation.PulseEffect

private const val ANIMATION_DURATION_IN_MILLIS = 2 * SECOND_IN_MILLIS.toInt()

@Composable
fun NewFeedingAnimation(
    sizeConfiguration: NewFeedingAnimationSizeConfiguration,
    pulseColors: Pair<Color, Color>,
    content: @Composable (Modifier) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "new feeding transition")

    PulseEffect(
        infiniteTransition = infiniteTransition,
        durationMillis = ANIMATION_DURATION_IN_MILLIS,
        minSize = sizeConfiguration.contentMinSize,
        maxSize = sizeConfiguration.pulseSize,
        easing = { fraction ->
            if (fraction.isSecondTimeSpan()) {
                fraction * 4 - 1 // run while the content increases
            } else {
                0f // wait
            }
        },
        colors = pulseColors
    )
    BacklightEffect(
        infiniteTransition = infiniteTransition,
        animationDurationInMillis = ANIMATION_DURATION_IN_MILLIS,
        easing = { fraction ->
            when {
                fraction.isFirstTimeSpan() -> 0f // transparent while the content decreases
                fraction.isSecondTimeSpan() -> fraction * 4 - 1 // gains alpha while the content increases
                else -> 1f // shown while the content is static
            }
        },
        size = sizeConfiguration.backlightSize
    )
    DecreasingEffect(
        infiniteTransition = infiniteTransition,
        initialSize = sizeConfiguration.contentMaxSize,
        targetSize = sizeConfiguration.contentMinSize,
        animationDurationInMillis = ANIMATION_DURATION_IN_MILLIS,
        easing = { fraction: Float ->
            when {
                fraction.isFirstTimeSpan() -> fraction * 4 // decrease for 0.5 second
                fraction.isSecondTimeSpan() -> 2 - fraction * 4 // increase for 0.5 second
                else -> 0f // static for 1 second
            }
        },
        content = content
    )
}

private fun Float.isFirstTimeSpan() = this <= 0.25f
private fun Float.isSecondTimeSpan() = this > 0.25f && this <= 0.5f

data class NewFeedingAnimationSizeConfiguration(
    val pulseSize: Dp,
    val backlightSize: Dp,
    val contentMinSize: Dp,
    val contentMaxSize: Dp
)