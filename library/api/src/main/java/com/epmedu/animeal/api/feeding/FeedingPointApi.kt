package com.epmedu.animeal.api.feeding

import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.api.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow

interface FeedingPointApi {
    fun getAllFeedingPoints(): Flow<List<FeedingPoint>>

    suspend fun startFeeding(feedingPointId: String): ApiResult<String>

    suspend fun cancelFeeding(feedingPointId: String): ApiResult<String>

    suspend fun finishFeeding(feedingPointId: String, images: List<String>): ApiResult<String>
}