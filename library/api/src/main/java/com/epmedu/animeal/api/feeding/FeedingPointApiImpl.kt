package com.epmedu.animeal.api.feeding

import CancelFeedingMutation
import FinishFeedingMutation
import OnCreateFeedingPointSubscription
import OnCreateFeedingPointSubscription.OnCreateFeedingPoint
import OnDeleteFeedingPointSubscription
import OnUpdateFeedingPointSubscription
import OnUpdateFeedingPointSubscription.OnUpdateFeedingPoint
import StartFeedingMutation
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.api.extensions.getModelList
import com.epmedu.animeal.api.extensions.performMutation
import com.epmedu.animeal.api.extensions.subscribe
import com.epmedu.animeal.api.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow

internal class FeedingPointApiImpl(
    private val awsAppSyncClient: AWSAppSyncClient
) : FeedingPointApi {

    override fun getAllFeedingPoints(): Flow<List<FeedingPoint>> {
        return getModelList()
    }

    override fun subscribeToFeedingPointsUpdates(): Flow<OnUpdateFeedingPoint> {
        return awsAppSyncClient.subscribe(
            subscription = OnUpdateFeedingPointSubscription(),
            getData = { onUpdateFeedingPoint() }
        )
    }

    override fun subscribeToFeedingPointsCreation(): Flow<OnCreateFeedingPoint> {
        return awsAppSyncClient.subscribe(
            subscription = OnCreateFeedingPointSubscription(),
            getData = { onCreateFeedingPoint() }
        )
    }

    override fun subscribeToFeedingPointsDeletion(): Flow<String> {
        return awsAppSyncClient.subscribe(
            subscription = OnDeleteFeedingPointSubscription(),
            getData = { onDeleteFeedingPoint()?.id() }
        )
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