package com.epmedu.animeal.favourites.presentation.viewmodel

import com.epmedu.animeal.feeding.domain.model.FeedingPoint

data class FavouritesState(
    val favourites: List<FeedingPoint> = emptyList(),
    val showingFeedSpot: FeedingPoint? = null,
    val showingWillFeedDialog: Boolean = false,
)