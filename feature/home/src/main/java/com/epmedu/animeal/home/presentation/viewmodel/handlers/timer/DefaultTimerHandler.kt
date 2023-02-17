package com.epmedu.animeal.home.presentation.viewmodel.handlers.timer

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.HOUR_IN_MILLIS
import com.epmedu.animeal.extensions.MINUTE_IN_MILLIS
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.timer.domain.AcceptTimerExpirationUseCase
import com.epmedu.animeal.timer.domain.StartTimerUseCase
import javax.inject.Inject

class DefaultTimerHandler @Inject constructor(
    stateDelegate: StateDelegate<HomeState>,
    private val startTimerUseCase: StartTimerUseCase,
    private val acceptTimerExpirationUseCase: AcceptTimerExpirationUseCase
) : TimerHandler, StateDelegate<HomeState> by stateDelegate {

    override fun handleTimerEvent(event: HomeScreenEvent.TimerEvent) {
        when (event) {
            HomeScreenEvent.TimerEvent.Started -> startTimer()
            HomeScreenEvent.TimerEvent.ExpirationAccepted -> acceptExpiration()
        }
    }

    private fun startTimer() {
        startTimerUseCase.invoke(HOUR_IN_MILLIS, MINUTE_IN_MILLIS)
    }

    private fun acceptExpiration() {
        acceptTimerExpirationUseCase.invoke()
    }
}