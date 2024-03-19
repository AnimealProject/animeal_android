package com.epmedu.animeal.feedings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.feedings.presentation.model.FeedingModelStatus
import com.epmedu.animeal.feedings.presentation.model.isPending
import com.epmedu.animeal.feedings.presentation.ui.FeedingItem
import com.epmedu.animeal.feedings.presentation.ui.FeedingItemButtons
import com.epmedu.animeal.feedings.presentation.ui.FeedingItemSheetContent
import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingFilterCategory
import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingsState
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue.Hidden
import com.epmedu.animeal.foundation.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.tabs.AnimealSwitchTab
import com.epmedu.animeal.foundation.tabs.TabIndicator
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun FeedingsScreenUI(
    state: FeedingsState,
    currentFeeding: FeedingModel?,
    bottomSheetState: AnimealBottomSheetState,
    onBack: () -> Unit,
    onFilterClick: (FeedingFilterCategory) -> Unit,
    onFeedingClick: (FeedingModel) -> Unit
) {
    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha(
        skipHalfExpanded = true
    )

    AnimealBottomSheetLayout(
        modifier = Modifier.statusBarsPadding(),
        skipHalfExpanded = true,
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetBackgroundColor = MaterialTheme.colors.onPrimary,
        sheetContent = {
            currentFeeding?.let {
                FeedingItemSheetContent(
                    feeding = it,
                    contentAlpha = contentAlpha,
                    modifier = Modifier.padding(top = 24.dp, bottom = 120.dp)
                )
            }
        },
        sheetControls = {
            currentFeeding?.let {
                FeedingItemButtons(
                    areEnabled = currentFeeding.status.isPending(),
                    onRejectClick = {},
                    onApproveClick = {},
                    modifier = Modifier
                        .alpha(buttonAlpha)
                        .padding(vertical = 40.dp)
                )
            }
        }
    ) {
        FeedingsScreenContent(onBack, state, onFeedingClick, onFilterClick)
    }
}

@Composable
private fun FeedingsScreenContent(
    onBack: () -> Unit,
    state: FeedingsState,
    onFeedingClick: (FeedingModel) -> Unit,
    onFilterClick: (FeedingFilterCategory) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(id = R.string.feedings),
            navigationIcon = {
                BackButton(onClick = onBack)
            }
        )
        Box(
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp, top = 20.dp)
        ) {
            when {
                state.isLoading -> {
                    LoadingState()
                }

                state.feedingsFiltered.isEmpty() -> {
                    EmptyState()
                }

                else -> {
                    FeedingList(
                        feedings = state.feedingsFiltered,
                        onFeedingClick = onFeedingClick
                    )
                }
            }
            FeedingsCategoryTab(state.feedingsCategory, onFilterClick)
        }
    }
}

@Composable
fun FeedingsCategoryTab(
    feedingsCategory: FeedingFilterCategory,
    onFilterClick: (FeedingFilterCategory) -> Unit
) {
    var selectedTab by remember { mutableStateOf(feedingsCategory) }

    Column {
        Card(elevation = 5.dp, shape = RoundedCornerShape(9.dp)) {
            TabRow(
                selectedTabIndex = selectedTab.ordinal,
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier
                    .height(36.dp),
                indicator = { tabPositions ->
                    TabIndicator(tabPositions, feedingsCategory)
                },
                divider = {}
            ) {
                FeedingFilterCategory.values().forEach { type ->
                    AnimealSwitchTab(
                        titleResId = type.title,
                        selected = selectedTab == type,
                        onClick = {
                            selectedTab = type
                            onFilterClick(type)
                        }
                    )
                }
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
private fun FeedingList(feedings: List<FeedingModel>, onFeedingClick: (FeedingModel) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 70.dp, bottom = 30.dp, start = 5.dp, end = 5.dp)
    ) {
        items(feedings) { feedingModel ->
            FeedingItem(
                feedingModel = feedingModel,
                onClick = { onFeedingClick(feedingModel) }
            )
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
            status = FeedingModelStatus.values().random(),
            elapsedTime = "12 hours ago"
        )
    }
    AnimealTheme {
        FeedingsScreenUI(
            state = FeedingsState(
                feedingsFiltered = feedings.toImmutableList(),
            ),
            currentFeeding = null,
            bottomSheetState = AnimealBottomSheetState(Hidden),
            onBack = {},
            onFilterClick = {},
            onFeedingClick = {}
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingScreenEmptyPreview() {
    AnimealTheme {
        FeedingsScreenUI(
            state = FeedingsState(),
            currentFeeding = null,
            bottomSheetState = AnimealBottomSheetState(Hidden),
            onBack = {},
            onFilterClick = {},
            onFeedingClick = {}
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingScreenLoadingPreview() {
    AnimealTheme {
        FeedingsScreenUI(
            state = FeedingsState(isLoading = true),
            currentFeeding = null,
            bottomSheetState = AnimealBottomSheetState(Hidden),
            onBack = {},
            onFilterClick = {},
            onFeedingClick = {}
        )
    }
}