package com.epmedu.animeal.timer.presentation.handler

import android.text.format.DateUtils.HOUR_IN_MILLIS
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.domain.usecase.DisableTimerUseCase
import com.epmedu.animeal.timer.domain.usecase.StartTimerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultTimerHandler @Inject constructor(
    routeHandler: RouteHandler,
    private val startTimerUseCase: StartTimerUseCase,
    private val disableTimerUseCase: DisableTimerUseCase
) : TimerHandler,
    RouteHandler by routeHandler {

    override fun CoroutineScope.handleTimerEvent(event: TimerEvent) {
        when (event) {
            TimerEvent.Disable -> launch { disableTimer() }
        }
    }

    override fun startTimer() {
        startTimerUseCase.invoke(HOUR_IN_MILLIS, MINUTE_IN_MILLIS)
    }

    override suspend fun disableTimer() {
        disableTimerUseCase.invoke()
    }
}