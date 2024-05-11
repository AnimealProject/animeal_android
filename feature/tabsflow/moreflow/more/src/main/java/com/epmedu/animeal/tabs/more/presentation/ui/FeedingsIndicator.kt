package com.epmedu.animeal.tabs.more.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedings.presentation.ui.NewFeedingAnimation
import com.epmedu.animeal.feedings.presentation.ui.NewFeedingAnimationSizeConfiguration
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
internal fun FeedingsIndicator() {
    val pulseSize = 10.dp

    Box(
        modifier = Modifier.size(pulseSize),
        contentAlignment = Alignment.Center
    ) {
        NewFeedingAnimation(
            sizeConfiguration = NewFeedingAnimationSizeConfiguration(
                pulseSize = pulseSize,
                backlightSize = 9.dp,
                contentMinSize = 3.dp,
                contentMaxSize = 5.dp
            ),
            pulseColors = if (isSystemInDarkTheme()) {
                Color.White to Color(0x00FFFFFF)
            } else {
                Color.Black to Color(0x00000000)
            }
        ) { modifier ->
            Box(
                modifier = modifier
                    .background(
                        color = MaterialTheme.colors.onBackground,
                        shape = CircleShape
                    )
            )
        }
    }
}

@AnimealPreview
@Composable
private fun FeedingsIndicatorPreview() {
    AnimealTheme {
        Box(
            modifier = Modifier.background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            FeedingsIndicator()
        }
    }
}