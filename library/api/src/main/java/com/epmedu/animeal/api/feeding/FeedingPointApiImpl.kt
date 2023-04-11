package com.epmedu.animeal.api.feeding

import com.amplifyframework.api.graphql.SubscriptionType
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.api.extensions.getModelList
import com.epmedu.animeal.api.extensions.subscribe
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
}