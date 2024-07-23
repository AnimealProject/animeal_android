package com.epmedu.animeal.timer.presentation.handler

sealed interface TimerEvent {
    data object Disable : TimerEvent
}