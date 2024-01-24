package com.epmedu.animeal.feedings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.feedings.presentation.model.FeedingModelStatus
import com.epmedu.animeal.feedings.presentation.ui.FeedingItem
import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingsState
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun FeedingsScreenUI(
    state: FeedingsState,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(id = R.string.feedings),
            navigationIcon = {
                BackButton(onClick = onBack)
            }
        )
        when {
            state.isLoading -> {
                LoadingState()
            }

            state.feedings.isEmpty() -> {
                EmptyState()
            }

            else -> {
                FeedingList(state.feedings)
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.no_feedings),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun FeedingList(feedings: List<FeedingModel>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(30.dp)
    ) {
        items(feedings) { feedingModel ->
            FeedingItem(feedingModel = feedingModel)
        }
    }
}

@AnimealPreview
@Composable
private fun FeedingScreenPreview() {
    val title = "FeedingPoint"
    val feedings = List(10) {
        FeedingModel(
            id = "0",
            title = title,
            feeder = "Feeder",
            status = FeedingModelStatus.values().getOrElse(it) { FeedingModelStatus.OUTDATED },
            elapsedTime = "12 hours ago"
        )
    }
    AnimealTheme {
        FeedingsScreenUI(
            state = FeedingsState(feedings.toImmutableList()),
            onBack = {}
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingScreenEmptyPreview() {
    AnimealTheme {
        FeedingsScreenUI(
            state = FeedingsState(),
            onBack = {}
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingScreenLoadingPreview() {
    AnimealTheme {
        FeedingsScreenUI(
            state = FeedingsState(isLoading = true),
            onBack = {}
        )
    }
}