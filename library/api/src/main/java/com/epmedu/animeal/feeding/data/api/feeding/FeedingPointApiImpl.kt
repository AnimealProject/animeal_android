package com.epmedu.animeal.feeding.data.api.feeding

import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.extensions.getModelList
import kotlinx.coroutines.flow.Flow

internal class FeedingPointApiImpl : FeedingPointApi {

    override fun getAllFeedingPoints(): Flow<List<FeedingPoint>> {
        return getModelList(modelType = FeedingPoint::class.java)
    }
}