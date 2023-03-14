package com.epmedu.animeal.timer.data.repository

import com.epmedu.animeal.common.timer.tickerFlow
import com.epmedu.animeal.timer.data.model.TimerState
import com.epmedu.animeal.timer.domain.TimerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class TimerRepositoryImpl : TimerRepository {

    private var timerState: TimerState = TimerState.Disabled

    private val timerFlow = MutableStateFlow(timerState)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var job: Job? = null

    override fun startTimer(timeInMillis: Long, intervalInMillis: Long) {
        job = coroutineScope.launch {
            tickerFlow(timeInMillis, intervalInMillis)
                .onEach { updateTimer(it) }
                .onCompletion { expireTimer() }
                .collect()
        }
    }

    override fun getTimerState() = timerFlow.asStateFlow()

    override suspend fun disableTimer() {
        job?.run { cancel() }
        timerState = TimerState.Disabled
        timerFlow.emit(timerState)
    }

    private suspend fun updateTimer(timeLeftInMillis: Long) {
        timerState = TimerState.Active(timeLeft = timeLeftInMillis)
        timerFlow.emit(timerState)
    }

    private suspend fun expireTimer() {
        if (timerState is TimerState.Active) {
            timerState = TimerState.Expired
            timerFlow.emit(timerState)
        }
    }
}