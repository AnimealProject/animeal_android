package com.epmedu.animeal.home.presentation.ui

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults.elevation
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.SECOND_IN_MILLIS
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Hidden
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Pulsating
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Static
import com.epmedu.animeal.foundation.animation.PulseEffect
import com.epmedu.animeal.foundation.animation.getDpTypeConverter
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

private const val ANIMATION_DURATION_IN_MILLIS = 2 * SECOND_IN_MILLIS.toInt()

@Composable
internal fun HomeFeedingsButton(
    feedingsButtonState: FeedingsButtonState,
    onClick: () -> Unit
) {
    val pulseSize = 90.dp

    Box(
        modifier = Modifier.size(pulseSize),
        contentAlignment = Alignment.Center
    ) {
        when (feedingsButtonState) {
            Pulsating -> PulsatingFeedingsButton(pulseSize, onClick)
            Static -> StaticFeedingsButton(onClick)
            else -> {}
        }
    }
}

@Composable
private fun BoxScope.PulsatingFeedingsButton(
    pulseSize: Dp,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "feeding button transition")
    val initialFabSize = 56.dp
    val targetFabSize = 52.dp

    PulseEffect(
        infiniteTransition = infiniteTransition,
        durationMillis = ANIMATION_DURATION_IN_MILLIS,
        easing = { fraction ->
            if (fraction.isSecondTimeSpan()) {
                fraction * 4 - 1 // run while the button increases
            } else {
                0f // wait
            }
        },
        minSize = targetFabSize,
        maxSize = pulseSize
    )
    FeedingsButtonBacklightEffect(infiniteTransition)
    DecreasingFeedingsButton(
        infiniteTransition = infiniteTransition,
        initialSize = initialFabSize,
        targetSize = targetFabSize,
        onClick = onClick
    )
}

private fun Float.isFirstTimeSpan() = this <= 0.25f
private fun Float.isSecondTimeSpan() = this > 0.25f && this <= 0.5f

@Composable
private fun FeedingsButtonBacklightEffect(infiniteTransition: InfiniteTransition) {
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.79f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = ANIMATION_DURATION_IN_MILLIS,
                easing = { fraction ->
                    when {
                        fraction.isFirstTimeSpan() -> 0f // transparent while the button decreases
                        fraction.isSecondTimeSpan() -> fraction * 4 - 1 // gains alpha while the button increases
                        else -> 1f // shown while the button is static
                    }
                },
            )
        ),
        label = "feedings button backlight alpha animation"
    )

    Box(
        modifier = Modifier
            .background(
                brush = Brush.radialGradient(
                    0.5f to Color(0xFFF04E45),
                    1f to Color(0x00EFB5B2)
                ),
                shape = CircleShape,
                alpha = alpha
            )
            .size(76.dp)
    )
}

@Composable
private fun DecreasingFeedingsButton(
    infiniteTransition: InfiniteTransition,
    initialSize: Dp,
    targetSize: Dp,
    onClick: () -> Unit
) {
    val size by infiniteTransition.animateValue(
        initialValue = initialSize,
        targetValue = targetSize,
        typeConverter = getDpTypeConverter(),
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = ANIMATION_DURATION_IN_MILLIS,
                easing = { fraction: Float ->
                    when {
                        fraction.isFirstTimeSpan() -> fraction * 4 // decrease for 0.5 second
                        fraction.isSecondTimeSpan() -> 2 - fraction * 4 // increase for 0.5 second
                        else -> 0f // static for 1 second
                    }
                }
            )
        ),
        label = "feedings button FAB animation"
    )

    StaticFeedingsButton(
        modifier = Modifier.size(size),
        onClick = onClick
    )
}

@Composable
private fun StaticFeedingsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.background,
        elevation = elevation(0.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pets),
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground,
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingsButtonPreview() {
    AnimealTheme {
        Column(
            modifier = Modifier.background(Color.Gray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeFeedingsButton(feedingsButtonState = Pulsating, onClick = {})
            Divider()
            HomeFeedingsButton(feedingsButtonState = Static, onClick = {})
            Divider()
            HomeFeedingsButton(feedingsButtonState = Hidden, onClick = {})
        }
    }
}