package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FeedingItemButtons(
    areEnabled: Boolean,
    onRejectClick: () -> Unit,
    onApproveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        AnimealSecondaryButtonOutlined(
            text = stringResource(id = R.string.reject),
            onClick = onRejectClick,
            modifier = Modifier.weight(1f),
            enabled = areEnabled
        )
        AnimealButton(
            text = stringResource(id = R.string.approve),
            onClick = onApproveClick,
            modifier = Modifier.weight(1f),
            enabled = areEnabled
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingItemButtonsPreview() {
    AnimealTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val buttons: @Composable (areEnabled: Boolean) -> Unit = { areEnabled ->
                FeedingItemButtons(
                    areEnabled = areEnabled,
                    onRejectClick = {},
                    onApproveClick = {}
                )
            }
            buttons(true)
            Divider()
            buttons(false)
        }
    }
}