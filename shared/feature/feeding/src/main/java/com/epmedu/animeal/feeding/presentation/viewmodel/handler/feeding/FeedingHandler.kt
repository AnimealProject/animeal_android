package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding

import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface FeedingHandler {

    var feedingStateFlow: StateFlow<FeedingPointState>
    suspend fun fetchCurrentFeeding()

    fun CoroutineScope.handleFeedingEvent(event: FeedingEvent)

    suspend fun cancelFeeding()

    suspend fun expireFeeding()
}