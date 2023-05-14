package com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding

import com.epmedu.animeal.home.presentation.HomeScreenEvent
import kotlinx.coroutines.CoroutineScope

internal interface FeedingHandler {

    suspend fun fetchCurrentFeeding()

    fun CoroutineScope.handleFeedingEvent(event: HomeScreenEvent.FeedingEvent)

    fun CoroutineScope.cancelFeeding()

    fun CoroutineScope.expireFeeding()
}