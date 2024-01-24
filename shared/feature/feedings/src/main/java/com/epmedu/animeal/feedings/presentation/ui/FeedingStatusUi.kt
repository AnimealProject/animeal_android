package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedings.presentation.model.FeedingModelStatus
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
internal fun FeedingStatusUi(
    status: FeedingModelStatus,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides status.color,
        ) {
            Icon(
                painter = painterResource(status.iconId),
                contentDescription = null
            )
            Text(
                text = stringResource(status.titleId),
                style = MaterialTheme.typography.caption,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}

@Composable
@AnimealPreview
private fun FeedingStatusUiPreview() {
    AnimealTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            FeedingStatusUi(status = FeedingModelStatus.APPROVED)
            Divider()
            FeedingStatusUi(status = FeedingModelStatus.PENDING_ORANGE)
            Divider()
            FeedingStatusUi(status = FeedingModelStatus.PENDING_GREY)
            Divider()
            FeedingStatusUi(status = FeedingModelStatus.PENDING_RED)
            Divider()
            FeedingStatusUi(status = FeedingModelStatus.REJECTED)
            Divider()
            FeedingStatusUi(status = FeedingModelStatus.OUTDATED)
        }
    }
}
