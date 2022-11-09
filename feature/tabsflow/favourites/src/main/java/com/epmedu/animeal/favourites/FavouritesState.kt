package com.epmedu.animeal.favourites

import com.epmedu.animeal.feeding.data.model.FeedingPoint

data class FavouritesState(
    val favourites: List<FeedingPoint> = emptyList(),
    val showingFeedSpot: FeedingPoint? = null,
)