package com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation

import com.epmedu.animeal.home.presentation.HomeScreenEvent
import kotlinx.coroutines.CoroutineScope

interface TimerCancellationHandler {

    fun CoroutineScope.handleTimerCancellationEvent(event: HomeScreenEvent.TimerCancellationEvent)
}