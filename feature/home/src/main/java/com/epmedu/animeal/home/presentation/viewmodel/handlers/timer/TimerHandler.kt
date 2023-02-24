package com.epmedu.animeal.home.presentation.viewmodel.handlers.timer

import com.epmedu.animeal.home.presentation.HomeScreenEvent
import kotlinx.coroutines.CoroutineScope

interface TimerHandler {
    fun CoroutineScope.handleTimerEvent(event: HomeScreenEvent.TimerEvent)

    fun startTimer()

    suspend fun disableTimer()
}