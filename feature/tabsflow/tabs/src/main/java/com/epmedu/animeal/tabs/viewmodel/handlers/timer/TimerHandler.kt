package com.epmedu.animeal.tabs.viewmodel.handlers.timer

import com.epmedu.animeal.home.presentation.TimerEvent

interface TimerHandler {

    fun handleTimerEvent(event: TimerEvent)
}