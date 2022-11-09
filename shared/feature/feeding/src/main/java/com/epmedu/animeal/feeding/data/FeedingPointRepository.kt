package com.epmedu.animeal.feeding.data

import com.epmedu.animeal.feeding.data.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FeedingPointRepository {

    fun getAllFeedingPoints(): Flow<List<FeedingPoint>>

    fun getCats(): Flow<List<FeedingPoint>>

    fun getDogs(): Flow<List<FeedingPoint>>

    fun getFavourites(): Flow<List<FeedingPoint>>

    fun getFeedingPoint(id: Int): Flow<FeedingPoint>
}