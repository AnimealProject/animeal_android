package com.epmedu.animeal.timer.data.model

sealed class TimerState {
    data object Disabled : TimerState()

    data class Active(val timeLeft: Long) : TimerState()

    data object Expired : TimerState()
}