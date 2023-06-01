package com.epmedu.animeal.tabs.search.presentation.viewmodel

import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.permissions.presentation.PermissionsState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val catsFeedingPoints: ImmutableList<FeedingPointModel> = persistentListOf(),
    val catsQuery: String = "",
    val dogsFeedingPoints: ImmutableList<FeedingPointModel> = persistentListOf(),
    val dogsQuery: String = "",
    val favourites: ImmutableList<FeedingPointModel> = persistentListOf(),
    val showingFeedingPoint: FeedingPointModel? = null,
    val feedState: FeedState = FeedState(),
    val permissionsState: PermissionsState = PermissionsState()
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