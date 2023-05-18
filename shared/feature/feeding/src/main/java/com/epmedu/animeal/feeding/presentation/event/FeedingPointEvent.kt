package com.epmedu.animeal.feeding.presentation.event

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.foundation.tabs.model.AnimalType

sealed interface FeedingPointEvent {
    data class Select(val feedingPoint: FeedingPointModel) : FeedingPointEvent
    object Deselect : FeedingPointEvent
    data class FavouriteChange(val isFavourite: Boolean) : FeedingPointEvent
    data class AnimalTypeChange(val type: AnimalType) : FeedingPointEvent
}