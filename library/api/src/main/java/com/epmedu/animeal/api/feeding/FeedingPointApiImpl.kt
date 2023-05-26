package com.epmedu.animeal.api.feeding

import com.amplifyframework.api.graphql.SubscriptionType
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.api.AnimealApi
import kotlinx.coroutines.flow.Flow

internal class FeedingPointApiImpl(
    private val animealApi: AnimealApi
) : FeedingPointApi {

    override fun getAllFeedingPoints(): Flow<List<FeedingPoint>> {
        return animealApi.getModelList(FeedingPoint::class.java)
    }

    override fun subscribeToFeedingPointsUpdates(): Flow<FeedingPoint> {
        return animealApi.launchSubscription(SubscriptionType.ON_UPDATE, FeedingPoint::class.java)
    }

    override fun subscribeToFeedingPointsCreation(): Flow<FeedingPoint> {
        return animealApi.launchSubscription(SubscriptionType.ON_CREATE, FeedingPoint::class.java)
    }

    override fun subscribeToFeedingPointsDeletion(): Flow<FeedingPoint> {
        return animealApi.launchSubscription(SubscriptionType.ON_DELETE, FeedingPoint::class.java)
    }
}