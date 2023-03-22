package com.epmedu.animeal.tabs.search.presentation.search

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.foundation.tabs.model.AnimalType

sealed interface SearchScreenEvent {
    data class FeedingPointSelected(val feedingPoint: FeedingPoint) : SearchScreenEvent
    object FeedingPointHidden : SearchScreenEvent

    data class FavouriteChange(
        val isFavourite: Boolean,
        val feedingPoint: FeedingPoint
    ) : SearchScreenEvent

    object ShowWillFeedDialog : SearchScreenEvent
    object DismissWillFeedDialog : SearchScreenEvent

    data class Search(val query: String, val animalType: AnimalType) : SearchScreenEvent
}
