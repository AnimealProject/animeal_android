package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FeedingPointRepository {

    fun getAllFeedingPoints(): Flow<List<FeedingPoint>>

    fun getFeedingPointsBy(predicate: (FeedingPoint) -> Boolean): Flow<List<FeedingPoint>>

    suspend fun getFeedingPointById(id: String): FeedingPoint
}