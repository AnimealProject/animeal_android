package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint

import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import kotlinx.coroutines.CoroutineScope

interface FeedingPointHandler {

    suspend fun fetchFeedingPoints()

    fun deselectFeedingPoint()

    suspend fun showFeedingPoint(feedingPointId: String)

    fun showSingleReservedFeedingPoint(feedingPoint: FeedingPointModel)

    fun CoroutineScope.handleFeedingPointEvent(event: FeedingPointEvent)
}