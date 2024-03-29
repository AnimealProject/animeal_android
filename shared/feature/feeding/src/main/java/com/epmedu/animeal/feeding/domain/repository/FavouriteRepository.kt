package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    fun getFavouriteFeedingPointIds(shouldFetch: Boolean = true): Flow<List<String>>

    suspend fun addFeedingPointToFavourites(feedingPointId: String): ActionResult<Unit>

    suspend fun removeFeedingPointFromFavourites(feedingPointId: String): ActionResult<Unit>
}