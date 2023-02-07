package com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import kotlinx.coroutines.flow.Flow

interface FeedingHandler {
    suspend fun fetchFeedingPoints()
    fun showSingleFeedingPoint(feedingPoint: FeedingPointModel)
    fun saveFeeder(): Flow<Boolean>
}