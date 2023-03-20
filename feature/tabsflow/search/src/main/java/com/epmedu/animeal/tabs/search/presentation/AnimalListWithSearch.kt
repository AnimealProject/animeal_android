package com.epmedu.animeal.tabs.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.tabs.search.domain.model.GroupFeedingPointsModel
import com.epmedu.animeal.tabs.search.presentation.search.AnimalExpandableList
import com.epmedu.animeal.tabs.search.presentation.search.SearchScreenEvent
import com.epmedu.animeal.tabs.search.presentation.search.SearchView
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AnimalListWithSearch(
    animalType: AnimalType,
    feedingPoints: ImmutableList<FeedingPoint>,
    query: String,
    onEvent: (SearchScreenEvent) -> Unit,
) {
    val sortedPoints = remember(feedingPoints) {
        feedingPoints.groupBy { it.city }.map { entry ->
            GroupFeedingPointsModel(
                title = entry.key, points = entry.value
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bottomBarHeight)
    ) {
        AnimalExpandableList(
            groupedPoints = sortedPoints,
            onEvent = onEvent,
            query = query
        ) {
            SearchView(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .padding(
                        top = 14.dp,
                        bottom = 14.dp
                    ),
                initialValue = query
            ) { textFieldValue ->
                onEvent(SearchScreenEvent.Search(textFieldValue.text, animalType))
            }
        }
    }
}

