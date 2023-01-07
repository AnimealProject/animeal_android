package com.epmedu.animeal.feeding.data.api.feeding

import com.epmedu.animeal.feeding.data.model.FeedingPointData
import kotlinx.coroutines.flow.Flow

interface FeedingPointApi {
    fun getAllFeedingPoints(): Flow<List<FeedingPointData>>
}