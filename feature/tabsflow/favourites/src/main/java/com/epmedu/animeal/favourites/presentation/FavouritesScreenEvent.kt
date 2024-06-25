package com.epmedu.animeal.favourites.presentation

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel

internal sealed interface FavouritesScreenEvent {
    data class FeedingPointSelected(val feedingPoint: FeedingPointModel) : FavouritesScreenEvent
    data object FeedingPointHidden : FavouritesScreenEvent

    data class FavouriteChange(
        val isFavourite: Boolean,
        val feedingPoint: FeedingPointModel
    ) : FavouritesScreenEvent
}