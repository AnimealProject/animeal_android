package com.epmedu.animeal.favourites.presentation

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.foundation.tabs.model.AnimalType

internal sealed interface FavouritesScreenEvent {
    data class FeedingPointSelected(val feedingPoint: FeedingPoint) : FavouritesScreenEvent
    object FeedingPointHidden : FavouritesScreenEvent

    data class FavouriteChange(
        val isFavourite: Boolean,
        val feedingPoint: FeedingPoint
    ) : FavouritesScreenEvent

    object ShowWillFeedDialog : FavouritesScreenEvent
    object DismissWillFeedDialog : FavouritesScreenEvent
    class ShowOnMap(val feedingPointId: String, val animalType: AnimalType) : FavouritesScreenEvent
}