package com.epmedu.animeal.tabs.search.presentation.dogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.tabs.search.domain.model.GroupFeedingPointsModel
import com.epmedu.animeal.tabs.search.presentation.search.AnimalExpandableList
import com.epmedu.animeal.tabs.search.presentation.search.SearchScreenEvent
import com.epmedu.animeal.tabs.search.presentation.search.SearchState
import com.epmedu.animeal.tabs.search.presentation.search.SearchView


@Composable
internal fun DogsContent(state: SearchState, onEvent: (SearchScreenEvent) -> Unit) {
    val sortedContacts = remember(state.feedingPoints) {
        state.feedingPoints.groupBy { it.city }
            .map { entry ->
                GroupFeedingPointsModel(
                    title = entry.key,
                    points = entry.value
                )
            }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bottomBarHeight)
    ) {
        AnimalExpandableList(
            groupedPoints = sortedContacts,
            onEvent = onEvent,
        ) {
            SearchView(
                modifier = Modifier
                    .padding(
                        horizontal = 30.dp,
                        vertical = 28.dp
                    ),
                initialValue = state.query
            ) { textFieldValue ->
                onEvent(SearchScreenEvent.Search(textFieldValue.text, AnimalType.Dogs))
            }
        }
    }


}