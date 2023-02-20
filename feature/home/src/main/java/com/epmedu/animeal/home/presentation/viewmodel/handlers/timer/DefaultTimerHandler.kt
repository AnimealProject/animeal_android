package com.epmedu.animeal.home.presentation.viewmodel.handlers.timer

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.HOUR_IN_MILLIS
import com.epmedu.animeal.extensions.MINUTE_IN_MILLIS
import com.epmedu.animeal.home.presentation.HomeScreenEvent.TimerEvent
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.timer.domain.AcceptTimerExpirationUseCase
import com.epmedu.animeal.timer.domain.StartTimerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class DefaultTimerHandler @Inject constructor(
    stateDelegate: StateDelegate<HomeState>,
    routeHandler: RouteHandler,
    feedingPointHandler: FeedingPointHandler,
    private val startTimerUseCase: StartTimerUseCase,
    private val acceptTimerExpirationUseCase: AcceptTimerExpirationUseCase
) : TimerHandler,
    FeedingPointHandler by feedingPointHandler,
    RouteHandler by routeHandler,
    StateDelegate<HomeState> by stateDelegate {

    override fun CoroutineScope.handleTimerEvent(event: TimerEvent) {
        when (event) {
            TimerEvent.Expired -> launch { expireTimer() }
            TimerEvent.ExpirationAccepted -> launch { disableTimer() }
        }
    }

    override fun startTimer() {
        startTimerUseCase.invoke(HOUR_IN_MILLIS, MINUTE_IN_MILLIS)
    }

    private suspend fun expireTimer() {
        stopRoute()
        fetchFeedingPoints()
    }

    override suspend fun disableTimer() {
        acceptTimerExpirationUseCase.invoke()
    }
}