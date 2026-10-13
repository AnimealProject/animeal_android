package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Badge
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Composable
fun FeedingPointInactiveBadge(
    modifier: Modifier = Modifier
) {
    Badge(
        modifier = modifier
            .offset(x = (-4).dp, y = (-6).dp),
        backgroundColor = CustomColor.DarkerGrey
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.caption,
            text = stringResource(R.string.inactive)
        )
    }
}

@Composable
@AnimealPreview
private fun FeedingPointInactiveBadgePreview() {
    AnimealTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            FeedingPointInactiveBadge()
        }
    }
}