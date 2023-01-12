package com.epmedu.animeal.feeding.data.api.feeding

import com.amplifyframework.datastore.generated.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FeedingPointApi {
    fun getAllFeedingPoints(): Flow<List<FeedingPoint>>
}