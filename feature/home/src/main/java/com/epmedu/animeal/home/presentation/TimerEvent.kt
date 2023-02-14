package com.epmedu.animeal.home.presentation

sealed interface TimerEvent {
    object Started : TimerEvent

    object ExpirationAccepted : TimerEvent
}