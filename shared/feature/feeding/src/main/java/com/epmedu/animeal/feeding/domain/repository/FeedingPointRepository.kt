package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FeedingPointRepository {

    fun getAllFeedingPoints(shouldFetch: Boolean = true): Flow<List<FeedingPoint>>

    fun getFeedingPointsBy(
        shouldFetch: Boolean = true,
        predicate: (FeedingPoint) -> Boolean
    ): Flow<List<FeedingPoint>>

    fun getFeedingPointById(id: String): FeedingPoint?
}