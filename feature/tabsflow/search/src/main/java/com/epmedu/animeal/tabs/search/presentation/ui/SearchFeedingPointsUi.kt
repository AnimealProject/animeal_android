package com.epmedu.animeal.tabs.search.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.presentation.model.toFeedStatus
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointItem
import com.epmedu.animeal.foundation.listitem.ExpandableListItem
import com.epmedu.animeal.foundation.search.SearchView
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.search.domain.model.GroupFeedingPointsModel
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent

@Composable
fun SearchFeedingPointsUi(
    feedingPoints: List<FeedingPoint>,
    query: String,
    animalType: AnimalType,
    onEvent: (SearchScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val groupedPoints = remember(feedingPoints) {
        feedingPoints.groupBy { it.city }.map { entry ->
            GroupFeedingPointsModel(
                title = entry.key,
                points = entry.value
            )
        }
    }

    val scrollState = rememberLazyListState()
    val isSearchResultsEmpty = groupedPoints.isEmpty() && query.isNotEmpty()

    LazyColumn(
        state = scrollState,
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 8.dp)
    ) {
        renderSearchView(query, animalType, onEvent)

        when {
            isSearchResultsEmpty -> renderEmptyListState(query)
            else -> renderGroupedFeedingPoints(query, groupedPoints, onEvent)
        }
    }
}

private fun LazyListScope.renderSearchView(
    query: String,
    animalType: AnimalType,
    onEvent: (SearchScreenEvent) -> Unit
) {
    item {
        SearchView(
            modifier = Modifier.padding(
                horizontal = 30.dp,
                vertical = 14.dp
            ),
            initialValue = query,
            onValueChange = { textFieldValue ->
                onEvent(SearchScreenEvent.Search(textFieldValue.text, animalType))
            }
        )
    }
}

private fun LazyListScope.renderGroupedFeedingPoints(
    query: String,
    groupedPoints: List<GroupFeedingPointsModel>,
    onEvent: (SearchScreenEvent) -> Unit
) {
    items(groupedPoints, key = { group -> group.title }) { group ->

        ExpandableListItem(
            key = query,
            title = group.title
        ) {
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
                        imageUrl = feedingPoint.images[0],
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