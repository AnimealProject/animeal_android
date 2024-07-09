package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding

import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface FeedingHandler {

    var feedingStateFlow: StateFlow<FeedState>

    fun CoroutineScope.fetchCurrentFeeding()

    fun CoroutineScope.handleFeedingEvent(event: FeedingEvent)

    fun CoroutineScope.cancelFeeding()

    fun CoroutineScope.expireFeeding()

    suspend fun dismissThankYouDialog()
}