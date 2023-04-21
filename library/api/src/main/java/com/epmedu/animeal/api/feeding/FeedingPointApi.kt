package com.epmedu.animeal.api.feeding

import com.amplifyframework.datastore.generated.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FeedingPointApi {

    fun getAllFeedingPoints(): Flow<List<FeedingPoint>>

    fun subscribeToFeedingPointsUpdates(): Flow<FeedingPoint>

    fun subscribeToFeedingPointsCreation(): Flow<FeedingPoint>

    fun subscribeToFeedingPointsDeletion(): Flow<FeedingPoint>
}