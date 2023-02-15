package com.epmedu.animeal.api.feeding

import CancelFeedingMutation
import FinishFeedingMutation
import StartFeedingMutation
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.api.extensions.getModelList
import com.epmedu.animeal.api.extensions.performMutation
import com.epmedu.animeal.api.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow

internal class FeedingPointApiImpl : FeedingPointApi {

    override fun getAllFeedingPoints(): Flow<List<FeedingPoint>> {
        return getModelList()
    }

    override suspend fun startFeeding(feedingPointId: String): ApiResult<String> {
        return StartFeedingMutation(feedingPointId).performMutation()
    }

    override suspend fun cancelFeeding(feedingPointId: String): ApiResult<String> {
        return CancelFeedingMutation(feedingPointId).performMutation()
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ApiResult<String> {
        return FinishFeedingMutation(feedingPointId, images).performMutation()
    }
}