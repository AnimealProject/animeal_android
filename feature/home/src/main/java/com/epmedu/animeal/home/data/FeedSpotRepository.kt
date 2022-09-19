package com.epmedu.animeal.home.data

import com.epmedu.animeal.home.data.model.FeedSpot
import kotlinx.coroutines.flow.Flow

interface FeedSpotRepository {
    suspend fun getFeedSpot(id: Int): Flow<FeedSpot>
}