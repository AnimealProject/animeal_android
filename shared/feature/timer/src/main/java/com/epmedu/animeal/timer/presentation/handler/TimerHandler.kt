package com.epmedu.animeal.timer.presentation.handler

import kotlinx.coroutines.CoroutineScope

interface TimerHandler {
    fun CoroutineScope.handleTimerEvent(event: TimerEvent)

    fun startTimer()

    suspend fun disableTimer()
}