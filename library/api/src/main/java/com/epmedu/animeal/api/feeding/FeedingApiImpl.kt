package com.epmedu.animeal.api.feeding

import CancelFeedingMutation
import FinishFeedingMutation
import RejectFeedingMutation
import StartFeedingMutation
import com.amplifyframework.datastore.generated.model.Feeding
import com.epmedu.animeal.api.extensions.getModelList
import com.epmedu.animeal.api.extensions.launch
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow

internal class FeedingApiImpl : FeedingApi {

    override fun getUserFeedings(userId: String): Flow<List<Feeding>> {
        return getModelList(predicate = Feeding.USER_ID.eq(userId))
    }

    override suspend fun startFeeding(feedingPointId: String): ApiResult<String> {
        return StartFeedingMutation(feedingPointId).launch()
    }

    override suspend fun cancelFeeding(feedingPointId: String): ApiResult<String> {
        return CancelFeedingMutation(
            feedingPointId,
            "reason"
        ).launch()
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ApiResult<String> {
        return RejectFeedingMutation(
            feedingPointId,
            reason,
            null
        ).launch()
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ApiResult<String> {
        return FinishFeedingMutation(feedingPointId, images).launch()
    }
}