package com.epmedu.animeal.home.data

import com.epmedu.animeal.home.data.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FeedingPointRepository {

    suspend fun getAllFeedingPoints(): Flow<List<FeedingPoint>>

    suspend fun getCats(): Flow<List<FeedingPoint>>

    suspend fun getDogs(): Flow<List<FeedingPoint>>

    suspend fun getFavourites(): Flow<List<FeedingPoint>>

    suspend fun getFeedingPoint(id: Int): Flow<FeedingPoint>
}