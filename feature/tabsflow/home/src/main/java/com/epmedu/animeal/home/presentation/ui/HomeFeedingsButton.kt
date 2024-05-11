package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults.elevation
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Hidden
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Pulsating
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Static
import com.epmedu.animeal.feedings.presentation.ui.NewFeedingAnimation
import com.epmedu.animeal.feedings.presentation.ui.NewFeedingAnimationSizeConfiguration
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

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
private fun PulsatingFeedingsButton(
    pulseSize: Dp,
    onClick: () -> Unit
) {
    NewFeedingAnimation(
        sizeConfiguration = NewFeedingAnimationSizeConfiguration(
            pulseSize = pulseSize,
            backlightSize = 76.dp,
            contentMinSize = 52.dp,
            contentMaxSize = 56.dp
        ),
        pulseColors = Color.White to Color(0x00FFFFFF)
    ) { modifier ->
        StaticFeedingsButton(
            modifier = modifier,
            onClick = onClick
        )
    }
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