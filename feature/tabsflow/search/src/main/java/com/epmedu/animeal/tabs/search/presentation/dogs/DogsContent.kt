package com.epmedu.animeal.tabs.search.presentation.dogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.tabs.search.presentation.search.AnimalExpandableList
import com.epmedu.animeal.tabs.search.presentation.search.SearchScreenEvent
import com.epmedu.animeal.tabs.search.presentation.search.SearchView


@Composable
internal fun DogsContent(dogsState: DogsState, onEvent: (SearchScreenEvent) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bottomBarHeight)
    ) {
        AnimalExpandableList(
            padding = PaddingValues(0.dp),
            groupedPoints = dogsState.groupFeedingPointsModels
        ) {
            SearchView(
                modifier = Modifier
                    .padding(
                        horizontal = 30.dp,
                        vertical = 28.dp
                    ),
                initialValue = dogsState.query
            ) { textFieldValue ->
                onEvent(SearchScreenEvent.Search(textFieldValue.text, AnimalType.Dogs))
            }
        }
    }


}