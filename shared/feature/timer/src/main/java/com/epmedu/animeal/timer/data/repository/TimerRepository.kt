package com.epmedu.animeal.timer.data.repository

import com.epmedu.animeal.timer.data.model.TimerState
import kotlinx.coroutines.flow.StateFlow

interface TimerRepository {

    suspend fun acceptTimerExpiration()

    fun startTimer(timeInMillis: Long, intervalInMillis: Long)

    fun getTimerState(): StateFlow<TimerState>
}