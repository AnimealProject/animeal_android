package com.epmedu.animeal.api.feeding

import ApproveFeedingMutation
import CancelFeedingMutation
import FinishFeedingMutation
import RejectFeedingMutation
import StartFeedingMutation
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult

internal class FeedingActionApiImpl(
    private val animealApi: AnimealApi
) : FeedingActionApi {

    override suspend fun startFeeding(feedingPointId: String): ApiResult<String> {
        return animealApi.launchMutation(
            mutation = StartFeedingMutation(feedingPointId),
            responseClass = String::class.java
        )
    }

    override suspend fun cancelFeeding(feedingPointId: String): ApiResult<String> {
        return animealApi.launchMutation(
            mutation = CancelFeedingMutation(feedingPointId, CANCEL_FEEDING_REASON),
            responseClass = String::class.java
        )
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ApiResult<String> {
        return animealApi.launchMutation(
            mutation = RejectFeedingMutation(feedingPointId, reason, null),
            responseClass = String::class.java
        )
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ApiResult<String> {
        return animealApi.launchMutation(
            mutation = FinishFeedingMutation(feedingPointId, images),
            responseClass = String::class.java
        )
    }

    override suspend fun approveFeeding(feedingPointId: String, reason: String): ApiResult<String> {
        return animealApi.launchMutation(
            mutation = ApproveFeedingMutation(feedingPointId, reason, null),
            responseClass = String::class.java
        )
    }

    private companion object {
        const val CANCEL_FEEDING_REASON = "reason"
    }
}