package com.epmedu.animeal.feedings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun FeedingsScreenContentUI(
    feedings: ImmutableList<FeedingModel>,
    padding: PaddingValues,
    onEvent: (FeedingsScreenEvent) -> Unit,
) {
    when {
        feedings.isEmpty() -> {
            EmptyState(padding)
        } else -> {
            FeedingList(feedings, onEvent)
        }
    }
}

@Composable
private fun EmptyState(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = "No items", // stringResource(R.string.feedings_no_items)
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun FeedingList(
    feedings: List<FeedingModel>,
    onEvent: (FeedingsScreenEvent) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(30.dp)

    ) {
        items(feedings) { feedingModel ->
            FeedingApproveItem(
                title = feedingModel.title,
                status = feedingModel.status,
                feededBy = feedingModel.user,
                feededDate = feedingModel.elapsedTime,
                imageUrl = feedingModel.image,
                onApporoveClick = { onEvent(FeedingsScreenEvent.FeedingApprove(feedingModel.id)) },
                onRejectClick = { onEvent(FeedingsScreenEvent.FeedingReject(feedingModel.id)) }
            )
        }
    }
}