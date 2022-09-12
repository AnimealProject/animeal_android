package com.epmedu.animeal.common.data.repository

import com.epmedu.animeal.common.data.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FeedingPointRepository {

    suspend fun getAllFeedingPoints(): Flow<List<FeedingPoint>>

    suspend fun getCats(): Flow<List<FeedingPoint>>

    suspend fun getDogs(): Flow<List<FeedingPoint>>

    suspend fun getFavourites(): Flow<List<FeedingPoint>>

}