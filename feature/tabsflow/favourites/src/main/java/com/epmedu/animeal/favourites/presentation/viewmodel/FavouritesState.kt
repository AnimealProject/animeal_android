package com.epmedu.animeal.favourites.presentation.viewmodel

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.permissions.presentation.PermissionsState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FavouritesState(
    val favourites: ImmutableList<FeedingPointModel> = persistentListOf(),
    val showingFeedingPoint: FeedingPointModel? = null,
    val permissionsState: PermissionsState = PermissionsState()
)