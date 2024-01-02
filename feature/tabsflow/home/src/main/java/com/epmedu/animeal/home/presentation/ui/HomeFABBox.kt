package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
internal fun HomeFABBox(
    feedingsButtonState: FeedingsButtonState,
    onGeoFABClick: () -> Unit,
    onFeedingsFABClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(vertical = 48.dp, horizontal = 12.dp)
    ) {
        Column {
            HomeFeedingsButton(
                feedingsButtonState = feedingsButtonState,
                onClick = onFeedingsFABClick
            )
            HeightSpacer(height = 51.dp)
        }
        GeoLocationFloatingActionButton(
            onClick = onGeoFABClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@AnimealPreview
@Composable
private fun HomeFABBoxPreview() {
    AnimealTheme {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .background(color = Color.Gray),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomeFABBox(
                feedingsButtonState = FeedingsButtonState.Static,
                onGeoFABClick = {},
                onFeedingsFABClick = {}
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            HomeFABBox(
                feedingsButtonState = FeedingsButtonState.Pulsating,
                onGeoFABClick = {},
                onFeedingsFABClick = {}
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            HomeFABBox(
                feedingsButtonState = FeedingsButtonState.Hidden,
                onGeoFABClick = {},
                onFeedingsFABClick = {}
            )
        }
    }
}