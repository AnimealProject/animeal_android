package com.epmedu.animeal.home.presentation.viewmodel.handlers.route

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingRouteCancellationRequest
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingRouteStartRequest
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingRouteUpdateRequest
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent.FeedingTimerUpdateRequest
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.viewmodel.HomeState

class DefaultRouteHandler(
    stateDelegate: StateDelegate<HomeState>
) : RouteHandler, StateDelegate<HomeState> by stateDelegate {

    override fun handleRouteEvent(event: HomeScreenEvent.RouteEvent) {
        when (event) {
            FeedingRouteCancellationRequest -> stopRoute()
            FeedingRouteStartRequest -> startRoute()
            is FeedingRouteUpdateRequest -> updateRoute(event)
            is FeedingTimerUpdateRequest -> updateTimer(event)
        }
    }

    private fun startRoute() {
        updateState {
            copy(feedingRouteState = FeedingRouteState.Started)
        }
    }

    private fun stopRoute() {
        updateState {
            copy(feedingRouteState = FeedingRouteState.Disabled)
        }
    }

    private fun updateRoute(event: FeedingRouteUpdateRequest) {
        updateState {
            copy(
                feedingRouteState = FeedingRouteState.Updated(
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
                feedingRouteState = FeedingRouteState.Updated(
                    state.feedingRouteState.distanceLeft,
                    event.timeLeft,
                    state.feedingRouteState.routeData
                )
            )
        }
    }
}