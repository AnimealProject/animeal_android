package com.epmedu.animeal.home.presentation.viewmodel.handlers.timer

import com.epmedu.animeal.home.presentation.HomeScreenEvent

interface TimerHandler {
    fun handleTimerEvent(event: HomeScreenEvent.TimerEvent)
}