package com.epmedu.animeal.feeding.data.api.feeding

import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.extensions.getModelList
import com.epmedu.animeal.feeding.data.model.FeedingPointData
import com.epmedu.animeal.feeding.data.model.FeedingPointData.Companion.toFeedingPointData
import kotlinx.coroutines.flow.Flow

internal class FeedingPointApiImpl : FeedingPointApi {
    override fun getAllFeedingPoints(): Flow<List<FeedingPointData>> {
        return getModelList(
            modelType = FeedingPoint::class.java,
            map = { toFeedingPointData() }
        )
    }
}