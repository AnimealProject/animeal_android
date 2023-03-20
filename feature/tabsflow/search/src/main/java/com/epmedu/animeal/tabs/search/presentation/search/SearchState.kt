package com.epmedu.animeal.tabs.search.presentation.search

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val catsFeedingPoints: ImmutableList<FeedingPoint> = persistentListOf(),
    val catsQuery: String = "",
    val dogsFeedingPoints: ImmutableList<FeedingPoint> = persistentListOf(),
    val dogsQuery: String = "",
    val favourites: ImmutableList<FeedingPoint> = persistentListOf(),
    val showingFeedingPoint: FeedingPoint? = null,
    val showingWillFeedDialog: Boolean = false
)