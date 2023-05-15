package com.epmedu.animeal.timer.presentation.handler

sealed interface TimerEvent {
    object Disable : TimerEvent
}