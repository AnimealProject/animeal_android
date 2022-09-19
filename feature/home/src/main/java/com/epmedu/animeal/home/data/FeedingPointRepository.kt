package com.epmedu.animeal.home.data

import com.epmedu.animeal.home.data.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FeedingPointRepository {

    suspend fun getFeedingPoint(id: Int): Flow<FeedingPoint>
}