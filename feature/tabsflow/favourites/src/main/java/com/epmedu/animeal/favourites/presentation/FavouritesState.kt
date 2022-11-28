package com.epmedu.animeal.favourites.presentation

import com.epmedu.animeal.feeding.data.model.FeedingPoint

data class FavouritesState(
    val favourites: List<FeedingPoint> = emptyList(),
    val showingFeedSpot: FeedingPoint? = null,
    val showingWillFeedDialog: Boolean = false,
)