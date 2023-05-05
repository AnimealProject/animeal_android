package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint

import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface FeedingPointHandler {

    var feedingPointStateFlow: StateFlow<FeedingPointState>
    fun CoroutineScope.registerFeedingPointState(updateCall: (FeedingPointState) -> Unit)

    suspend fun fetchFeedingPoints()

    fun deselectFeedingPoint()

    suspend fun showFeedingPoint(feedingPointId: String)

    fun showSingleReservedFeedingPoint(feedingPoint: FeedingPointModel)

    fun CoroutineScope.handleFeedingPointEvent(event: FeedingPointEvent)
}