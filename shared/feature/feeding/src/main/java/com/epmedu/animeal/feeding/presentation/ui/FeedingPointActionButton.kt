package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun FeedingPointActionButton(
    alpha: Float,
    onFeedClick: () -> Unit,
    onInformClick: () -> Unit,
    enabled: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .alpha(alpha),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnimealButton(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.i_will_feed),
            onClick = onFeedClick,
            enabled = enabled,
        )
        AnimealButton(
            onClick = onInformClick,
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = Color.White,
        ) {
            Icon(imageVector = Icons.Default.ChatBubbleOutline, contentDescription = null)
        }
    }
}

@Composable
@AnimealPreview
fun FeedingPointActionButtonPreview() {
    AnimealTheme {
        FeedingPointActionButton(alpha = 1.0f, onFeedClick = {}, onInformClick = {})
    }
}