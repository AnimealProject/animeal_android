package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun MarkFeedingDoneActionButton(
    alpha: Float,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    AnimealButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .alpha(alpha),
        text = stringResource(R.string.finish),
        onClick = onClick,
        enabled = enabled,
    )
}

@Composable
@AnimealPreview
private fun MarkFeedingDoneActionButtonPreview() {
    AnimealTheme {
        MarkFeedingDoneActionButton(
            alpha = 1.0f,
            enabled = true,
            onClick = {}
        )
    }
}