package com.epmedu.animeal.home.presentation.viewmodel.handlers.route

import android.os.CountDownTimer
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.HOUR_IN_MILLIS
import com.epmedu.animeal.extensions.MINUTE_IN_MILLIS
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingRouteCancellationRequest
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingRouteStartRequest
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingRouteUpdateRequest
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingTimerUpdateRequest
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding.FeedingHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultRouteHandler @Inject constructor(
    stateDelegate: StateDelegate<HomeState>,
    feedingHandler: FeedingHandler
) : RouteHandler,
    FeedingHandler by feedingHandler,
    StateDelegate<HomeState> by stateDelegate {

    private val timer = object : CountDownTimer(HOUR_IN_MILLIS, MINUTE_IN_MILLIS) {
        override fun onTick(timeLeftInMillis: Long) {
            handleRouteEvent(FeedingTimerUpdateRequest(timeLeftInMillis))
        }

        override fun onFinish() {
            onTimerExpire?.apply { invoke() }
            expireRoute()
            cancel()
        }
    }

    private var onTimerExpire: (() -> Unit)? = null

    override fun handleRouteEvent(
        event: HomeScreenEvent.RouteEvent,
        scope: CoroutineScope?
    ) {
        when (event) {
            FeedingRouteCancellationRequest -> {
                stopRoute()
                timer.cancel()
                scope?.launch { fetchFeedingPoints() }
            }
            is FeedingRouteStartRequest -> {
                scope?.launch {
                    saveFeeder().collect { result ->
                        if (saveFeederWasSuccessful(result)) {
                            startRoute()
                            onTimerExpire = event.onTimerExpire
                            timer.start()
                        } else {
                            updateRouteError()
                        }
                    }
                }
            }
            is FeedingRouteUpdateRequest -> updateRoute(event)
            is FeedingTimerUpdateRequest -> updateTimer(event)
        }
    }

    private fun startRoute() {
        val reservedFeedingPoint = state.currentFeedingPoint?.copy(animalStatus = AnimalState.YELLOW)
        reservedFeedingPoint?.let { showSingleFeedingPoint(FeedingPointModel(it)) }
        updateState {
            copy(
                currentFeedingPoint = reservedFeedingPoint,
                feedingRouteState = FeedingRouteState.Active()
            )
        }
    }

    private fun stopRoute() {
        updateState {
            copy(feedingRouteState = FeedingRouteState.Disabled)
        }
    }

    private fun expireRoute() {
        val canceledFeedingPoint = state.currentFeedingPoint?.copy(animalStatus = AnimalState.RED)
        canceledFeedingPoint?.let { showSingleFeedingPoint(FeedingPointModel(it)) }
        updateState {
            copy(
                currentFeedingPoint = canceledFeedingPoint,
                feedingRouteState = FeedingRouteState.TimerExpired
            )
        }
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

    private fun updateRouteError() {
        updateState {
            copy(feedingRouteState = FeedingRouteState.Active(isError = true))
        }
    }

    private fun saveFeederWasSuccessful(result: Boolean): Boolean {
        return result && state.currentFeedingPoint != null
    }
}