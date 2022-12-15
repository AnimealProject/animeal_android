package com.epmedu.animeal.favourites.presentation.viewmodel

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FavouritesState(
    val favourites: ImmutableList<FeedingPoint> = persistentListOf(),
    val showingFeedSpot: FeedingPoint? = null,
    val showingWillFeedDialog: Boolean = false,
)