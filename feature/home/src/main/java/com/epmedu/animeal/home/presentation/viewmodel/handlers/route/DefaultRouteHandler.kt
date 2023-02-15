package com.epmedu.animeal.home.presentation.viewmodel.handlers.route

import android.os.CountDownTimer
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.HOUR_IN_MILLIS
import com.epmedu.animeal.extensions.MINUTE_IN_MILLIS
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingRouteUpdateRequest
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingTimerUpdateRequest
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import javax.inject.Inject

internal class DefaultRouteHandler @Inject constructor(
    stateDelegate: StateDelegate<HomeState>
) : RouteHandler,
    StateDelegate<HomeState> by stateDelegate {

    private val timer = object : CountDownTimer(HOUR_IN_MILLIS, MINUTE_IN_MILLIS) {
        override fun onTick(timeLeftInMillis: Long) {
            handleRouteEvent(FeedingTimerUpdateRequest(timeLeftInMillis))
        }

        override fun onFinish() {
            cancel()
        }
    }

    override fun handleRouteEvent(event: RouteEvent) {
        when (event) {
            is FeedingRouteUpdateRequest -> updateRoute(event)
            is FeedingTimerUpdateRequest -> updateTimer(event)
        }
    }

    override fun startRouteAndTimer() {
        updateState { copy(feedingRouteState = FeedingRouteState.Active(), isError = false) }
        timer.start()
    }

    override fun stopRouteAndTimer() {
        updateState { copy(feedingRouteState = FeedingRouteState.Disabled) }
        timer.cancel()
    }

    private fun updateRoute(event: FeedingRouteUpdateRequest) {
        updateState {
            copy(
                feedingRouteState = FeedingRouteState.Active(
                    event.result.distanceLeft,
                    state.feedingRouteState.timeLeft,
                    event.result.routeData
                )
            )
        }
    }

    private fun updateTimer(event: FeedingTimerUpdateRequest) {
        updateState {
            copy(
                feedingRouteState = FeedingRouteState.Active(
                    state.feedingRouteState.distanceLeft,
                    event.timeLeft,
                    state.feedingRouteState.routeData
                )
            )
        }
    }
}