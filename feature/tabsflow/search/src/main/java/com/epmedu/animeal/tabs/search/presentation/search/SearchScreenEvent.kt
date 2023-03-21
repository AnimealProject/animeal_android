package com.epmedu.animeal.tabs.search.presentation.search

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.tabs.search.domain.model.GroupFeedingPointsModel

sealed interface SearchScreenEvent {
    data class FeedingPointSelected(val feedingPoint: FeedingPoint) : SearchScreenEvent
    object FeedingPointHidden : SearchScreenEvent

    data class FavouriteChange(
        val isFavourite: Boolean,
        val feedingPoint: FeedingPoint
    ) : SearchScreenEvent

    data class GroupChanged(val isExpanded: Boolean, val feedingPoint: GroupFeedingPointsModel) :
        SearchScreenEvent

    object ShowWillFeedDialog : SearchScreenEvent
    object DismissWillFeedDialog : SearchScreenEvent

    data class Search(val query: String, val animalType: AnimalType) : SearchScreenEvent
}
