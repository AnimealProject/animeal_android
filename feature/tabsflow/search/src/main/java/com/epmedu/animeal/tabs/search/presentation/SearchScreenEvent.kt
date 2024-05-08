package com.epmedu.animeal.tabs.search.presentation

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.foundation.tabs.model.AnimalType

sealed interface SearchScreenEvent {
    object ScreenCreated : SearchScreenEvent
    data class FeedingPointSelected(val feedingPoint: FeedingPointModel) : SearchScreenEvent
    object FeedingPointHidden : SearchScreenEvent

    data class FavouriteChange(
        val isFavourite: Boolean,
        val feedingPoint: FeedingPointModel
    ) : SearchScreenEvent

    data class Search(val query: String, val animalType: AnimalType) : SearchScreenEvent
    data class AnimalTypeChange(val animalType: AnimalType) : SearchScreenEvent
}
