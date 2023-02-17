package com.epmedu.animeal.timer.data.repository

import android.os.CountDownTimer
import com.epmedu.animeal.timer.data.model.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class TimerRepositoryImpl : TimerRepository {

    private var timerState: TimerState = TimerState.Disabled

    private val timerFlow = MutableStateFlow(timerState)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun startTimer(timeInMillis: Long, intervalInMillis: Long) {
        countDownTimer(timeInMillis, intervalInMillis).start()
    }

    override fun getTimerState() = timerFlow.asStateFlow()

    override fun acceptTimerExpiration() {
        coroutineScope.launch {
            timerState = TimerState.Disabled
            timerFlow.emit(timerState)
        }
    }

    private fun countDownTimer(timeInMillis: Long, intervalInMillis: Long) =
        object : CountDownTimer(timeInMillis, intervalInMillis) {
            override fun onTick(timeLeftInMillis: Long) {
                coroutineScope.launch {
                    updateTimer(timeLeftInMillis)
                }
            }

            override fun onFinish() {
                coroutineScope.launch {
                    expireTimer()
                }
                cancel()
            }
        }

    private suspend fun updateTimer(timeLeftInMillis: Long) {
        timerState = TimerState.Active(timeLeft = timeLeftInMillis)
        timerFlow.emit(timerState)
    }

    private suspend fun expireTimer() {
        timerState = TimerState.Expired
        timerFlow.emit(timerState)
    }
}