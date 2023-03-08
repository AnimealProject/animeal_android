package com.epmedu.animeal.tabs.search.presentation.search

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.tabs.search.presentation.dogs.DogsState

data class SearchState(
    val dogsState: DogsState = DogsState(),
    val showingFeedSpot: FeedingPoint? = null,
    val showingWillFeedDialog: Boolean = false
)