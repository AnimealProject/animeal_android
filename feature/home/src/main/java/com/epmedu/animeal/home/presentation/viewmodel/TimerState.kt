package com.epmedu.animeal.home.presentation.viewmodel

sealed class TimerState {
    object Disabled : TimerState()

    data class Active(val timeLeft: Long) : TimerState()

    object Expired : TimerState()
}