package com.epmedu.animeal.favourites.presentation.viewmodel

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.permissions.presentation.PermissionsState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FavouritesState(
    val favourites: ImmutableList<FeedingPoint> = persistentListOf(),
    val showingFeedingPoint: FeedingPoint? = null,
    val permissionsState: PermissionsState = PermissionsState(),
    val feedState: FeedState = FeedState(),
)