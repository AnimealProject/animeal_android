package com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding

import com.epmedu.animeal.home.presentation.HomeScreenEvent
import kotlinx.coroutines.CoroutineScope

internal interface FeedingHandler {

    fun CoroutineScope.handleFeedingEvent(event: HomeScreenEvent.FeedingEvent)

    suspend fun cancelFeeding()
}