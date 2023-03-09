package com.epmedu.animeal.tabs.search.presentation.search

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val feedingPoints: ImmutableList<FeedingPoint> = persistentListOf(),
    val query: String = "",
    val showingFeedSpot: FeedingPoint? = null,
    val showingWillFeedDialog: Boolean = false
)