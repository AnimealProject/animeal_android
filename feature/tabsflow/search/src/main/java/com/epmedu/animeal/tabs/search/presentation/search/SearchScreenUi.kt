package com.epmedu.animeal.tabs.search.presentation.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedconfirmation.presentation.FeedConfirmationDialog
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.toFeedStatus
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointActionButton
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointItem
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.foundation.listitem.ExpandableListItem
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.tabs.AnimealPagerTabRow
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.search.domain.model.GroupFeedingPointsModel
import com.epmedu.animeal.tabs.search.presentation.AnimalListWithSearch
import com.epmedu.animeal.tabs.search.presentation.viewmodel.SearchState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
internal fun SearchScreenUi(
    state: SearchState,
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (SearchScreenEvent) -> Unit
) {
    HandleFeedingPointSheetHiddenState(bottomSheetState, onEvent)

    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()

    val scope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    ScreenScaffold(
        bottomSheetState,
        state,
        contentAlpha,
        buttonAlpha,
        scope,
        onEvent
    )
}

@Composable
private fun ScreenScaffold(
    bottomSheetState: AnimealBottomSheetState,
    state: SearchState,
    contentAlpha: Float,
    buttonAlpha: Float,
    scope: CoroutineScope,
    onEvent: (SearchScreenEvent) -> Unit
) {
    AnimealBottomSheetLayout(
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 20.dp),
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        skipHalfExpanded = true,
        sheetContent = {
            state.showingFeedingPoint?.let { feedingPoint ->
                FeedingPointSheetContent(
                    feedingPoint = FeedingPointModel(
                        feedingPoint
                    ),
                    contentAlpha = contentAlpha,
                    modifier = Modifier.fillMaxHeight(),
                    isShowOnMapVisible = true,
                    onFavouriteChange = { isFavourite ->
                        onEvent(SearchScreenEvent.FavouriteChange(isFavourite, feedingPoint))
                    }
                )
            }
        },
        sheetControls = {
            FeedingPointActionButton(
                alpha = buttonAlpha,
                enabled = state.showingFeedingPoint?.animalStatus == AnimalState.RED,
                onClick = { onEvent(SearchScreenEvent.ShowWillFeedDialog) },
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.secondaryVariant),
        ) { padding ->
            ScreenContent(state, scope, onEvent)
        }
    }

    WillFeedConfirmationDialog(state, onEvent)
}

@Composable
private fun HandleFeedingPointSheetHiddenState(
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (SearchScreenEvent) -> Unit
) {
    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.isHidden }.distinctUntilChanged().filter { it }.collect {
            onEvent(SearchScreenEvent.FeedingPointHidden)
        }
    }
}

@Composable
internal fun WillFeedConfirmationDialog(
    state: SearchState,
    onEvent: (SearchScreenEvent) -> Unit
) {
    if (state.showingWillFeedDialog) {
        FeedConfirmationDialog(
            onAgreeClick = { onEvent(SearchScreenEvent.DismissWillFeedDialog) },
            onCancelClick = { onEvent(SearchScreenEvent.DismissWillFeedDialog) }
        )
    }
}

@Composable
private fun ScreenContent(
    state: SearchState,
    scope: CoroutineScope,
    onEvent: (SearchScreenEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
    ) {
        AnimalTypeHorizontalPager(
            tabRowHorizontalPadding = 30.dp,
            scope = scope
        ) { animalType ->

            when (animalType) {
                AnimalType.Dogs -> {
                    AnimalListWithSearch(
                        animalType = animalType,
                        feedingPoints = state.dogsFeedingPoints,
                        query = state.dogsQuery,
                        onEvent = onEvent,
                    )
                }

                AnimalType.Cats -> {
                    AnimalListWithSearch(
                        animalType = animalType,
                        feedingPoints = state.catsFeedingPoints,
                        query = state.catsQuery,
                        onEvent = onEvent,
                    )
                }
            }
        }
    }
}

@Composable
fun AnimalExpandableList(
    groupedPoints: List<GroupFeedingPointsModel>,
    query: String,
    onEvent: (SearchScreenEvent) -> Unit,
    topItemContent: @Composable (LazyItemScope.() -> Unit),
) {
    val scrollState = rememberLazyListState()
    val isSearchResultsEmpty = groupedPoints.isEmpty() && query.isNotEmpty()

    LazyColumn(
        state = scrollState,
        modifier = Modifier.padding(top = 12.dp),
        contentPadding = PaddingValues(bottom = 8.dp)
    ) {
        item { topItemContent() }

        if (isSearchResultsEmpty) {
            renderEmptyListState(query)
        } else {
            renderGroupedFeedingPoints(groupedPoints, onEvent)
        }
    }
}

private fun LazyListScope.renderGroupedFeedingPoints(
    groupedPoints: List<GroupFeedingPointsModel>,
    onEvent: (SearchScreenEvent) -> Unit
) {
    items(groupedPoints, key = { group -> group.title }) { group ->

        ExpandableListItem(title = group.title) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                group.points.forEach { feedingPoint ->
                    FeedingPointItem(
                        title = feedingPoint.title,
                        status = feedingPoint.animalStatus.toFeedStatus(),
                        isFavourite = feedingPoint.isFavourite,
                        onFavouriteChange = { isFavourite ->
                            onEvent(
                                SearchScreenEvent.FavouriteChange(
                                    isFavourite,
                                    feedingPoint
                                )
                            )
                        },
                        onClick = { onEvent(SearchScreenEvent.FeedingPointSelected(feedingPoint)) }
                    )
                }
            }
        }
    }
}

private fun LazyListScope.renderEmptyListState(query: String) {
    item {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillParentMaxHeight(0.7f)
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = stringResource(R.string.search_no_items, query),
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnimalTypeHorizontalPager(
    tabRowHorizontalPadding: Dp = 0.dp,
    scope: CoroutineScope,
    content: @Composable (animalType: AnimalType) -> Unit,
) {
    val pages = remember { AnimalType.values() }
    val pagerState = rememberPagerState()

    Column {
        AnimealPagerTabRow(
            modifier = Modifier.padding(horizontal = tabRowHorizontalPadding),
            pagerState = pagerState,
            onSelectTab = { tabIndex ->
                scope.launch {
                    pagerState.animateScrollToPage(tabIndex)
                }
            }
        )

        HorizontalPager(count = pages.size, state = pagerState, content = { page ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                content(pages[page])
            }
        })
    }
}

@AnimealPreview
@Composable
private fun SearchScreenUiPreview() {
    AnimealTheme {
        SearchScreenUi(
            SearchState(),
            AnimealBottomSheetState(AnimealBottomSheetValue.Hidden),
        ) {}
    }
}