package com.epmedu.animeal.api.feeding

import CancelFeedingMutation
import FinishFeedingMutation
import StartFeedingMutation
import com.amplifyframework.api.graphql.SubscriptionType
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.api.extensions.getModelList
import com.epmedu.animeal.api.extensions.performMutation
import com.epmedu.animeal.api.extensions.subscribe
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow

internal class FeedingPointApiImpl : FeedingPointApi {

    override fun getAllFeedingPoints(): Flow<List<FeedingPoint>> {
        return getModelList()
    }

    override fun subscribeToFeedingPointsUpdates(): Flow<FeedingPoint> {
        return subscribe(SubscriptionType.ON_UPDATE)
    }

    override fun subscribeToFeedingPointsCreation(): Flow<FeedingPoint> {
        return subscribe(SubscriptionType.ON_CREATE)
    }

    override fun subscribeToFeedingPointsDeletion(): Flow<FeedingPoint> {
        return subscribe(SubscriptionType.ON_DELETE)
    }

    override suspend fun startFeeding(feedingPointId: String): ApiResult<String> {
        return StartFeedingMutation(feedingPointId).performMutation()
    }

    override suspend fun cancelFeeding(feedingPointId: String): ApiResult<String> {
        return CancelFeedingMutation(
            feedingPointId,
            "reason"
        ).performMutation()
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ApiResult<String> {
        return FinishFeedingMutation(feedingPointId, images).performMutation()
    }
}