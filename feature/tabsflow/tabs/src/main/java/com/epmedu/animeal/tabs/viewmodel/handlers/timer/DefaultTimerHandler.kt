package com.epmedu.animeal.tabs.viewmodel.handlers.timer

import android.os.CountDownTimer
import android.text.format.DateUtils
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.presentation.TimerEvent
import com.epmedu.animeal.home.presentation.viewmodel.TimerState
import javax.inject.Inject

class DefaultTimerHandler @Inject constructor(
    stateDelegate: StateDelegate<TimerState>
) : TimerHandler,
    StateDelegate<TimerState> by stateDelegate {

    private val timer =
        object : CountDownTimer(DateUtils.HOUR_IN_MILLIS, DateUtils.MINUTE_IN_MILLIS) {
            override fun onTick(timeLeftInMillis: Long) {
                updateTimer(timeLeftInMillis)
            }

            override fun onFinish() {
                expireTimer()
                cancel()
            }
        }

    override fun handleTimerEvent(event: TimerEvent) {
        when (event) {
            TimerEvent.Started -> startTimer()
            TimerEvent.ExpirationAccepted -> timerExpirationAccepted()
        }
    }

    private fun startTimer() {
        timer.start()
    }

    private fun timerExpirationAccepted() {
        updateState {
            TimerState.Disabled
        }
    }

    private fun updateTimer(timeLeftInMillis: Long) {
        updateState {
            TimerState.Active(timeLeftInMillis)
        }
    }

    private fun expireTimer() {
        updateState {
            TimerState.Expired
        }
    }
}