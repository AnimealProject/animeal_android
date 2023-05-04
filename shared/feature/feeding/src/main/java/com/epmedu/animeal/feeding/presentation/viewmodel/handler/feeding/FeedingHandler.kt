package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding

import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import kotlinx.coroutines.CoroutineScope

interface FeedingHandler {

    suspend fun fetchCurrentFeeding()

    fun CoroutineScope.handleFeedingEvent(event: FeedingEvent)

    suspend fun cancelFeeding()

    suspend fun expireFeeding()
}