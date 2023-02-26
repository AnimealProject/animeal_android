package com.epmedu.animeal.timer.data.model

sealed class TimerState {
    object Disabled : TimerState()

    data class Active(val timeLeft: Long) : TimerState()

    object Expired : TimerState()
}