package com.epmedu.animeal.tabs.search.presentation.dogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.tabs.search.presentation.AnimalExpandableList
import com.epmedu.animeal.tabs.search.presentation.SearchView


@Composable
fun DogsContent(dogsState: DogsState) {
    val state = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bottomBarHeight)
    ) {
        AnimalExpandableList(
            padding = PaddingValues(0.dp),
            groupedPoints = dogsState.groupFeedingPointsModels
        ) {
            SearchView(state, Modifier.padding(horizontal = 30.dp, vertical = 28.dp))
        }
    }
}