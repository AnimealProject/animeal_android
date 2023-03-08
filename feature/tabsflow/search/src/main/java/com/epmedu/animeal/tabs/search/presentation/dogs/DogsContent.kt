package com.epmedu.animeal.tabs.search.presentation.dogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.tabs.search.presentation.AnimalExpandableList


@Composable
fun DogsContent(dogsState: DogsState) {
    AnimalExpandableList(
        padding = PaddingValues(0.dp),
        groupedPoints = dogsState.groupFeedingPointsModels
    )
}