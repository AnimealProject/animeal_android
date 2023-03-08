package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    fun getFavouriteFeedingPointIds(): Flow<List<String>>

    suspend fun addFeedingPointToFavourites(feedingPointId: String): ActionResult

    suspend fun removeFeedingPointFromFavourites(feedingPointId: String): ActionResult
}