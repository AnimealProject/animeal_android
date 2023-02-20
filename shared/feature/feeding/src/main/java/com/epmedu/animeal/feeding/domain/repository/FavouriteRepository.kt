package com.epmedu.animeal.feeding.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    fun getFavouriteFeedingPointIds(): Flow<List<String>>

    suspend fun addFavouriteFeedingPoint(feedingPointId: String)

    suspend fun deleteFavouriteFeedingPoint(feedingPointId: String)
}