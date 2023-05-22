package com.epmedu.animeal.tabs.search.presentation.viewmodel

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.permissions.presentation.PermissionsState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val catsFeedingPoints: ImmutableList<FeedingPoint> = persistentListOf(),
    val catsQuery: String = "",
    val dogsFeedingPoints: ImmutableList<FeedingPoint> = persistentListOf(),
    val dogsQuery: String = "",
    val favourites: ImmutableList<FeedingPoint> = persistentListOf(),
    val showingFeedingPoint: FeedingPoint? = null,
    val permissionsState: PermissionsState = PermissionsState(),
    val feedState: FeedState = FeedState(),
) {
    fun getQueryBy(animalType: AnimalType) = when (animalType) {
        AnimalType.Dogs -> dogsQuery
        AnimalType.Cats -> catsQuery
    }

    fun getFeedingPointsBy(animalType: AnimalType) = when (animalType) {
        AnimalType.Dogs -> dogsFeedingPoints
        AnimalType.Cats -> catsFeedingPoints
    }
}