package com.epmedu.animeal.router.presentation

import com.epmedu.animeal.common.constants.Arguments.FORCED_FEEDING_POINT_ID
import com.epmedu.animeal.common.domain.usecase.ForcedArgumentsUseCase
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate

class DefaultRouteHandler(
    stateDelegate: StateDelegate<FeedingRouteState>,
    private val forcedArgumentsUseCase: ForcedArgumentsUseCase
) : RouteHandler,
    StateDelegate<FeedingRouteState> by stateDelegate {

    private var showFullRoad: Boolean = false

    override fun handleRouteEvent(event: RouteEvent) {
        when (event) {
            is RouteEvent.FeedingRouteUpdateRequest -> updateRoute(event)
            is RouteEvent.FeedingTimerUpdateRequest -> updateTimer(event)
        }
    }

    override fun startRoute() {
        if (forcedArgumentsUseCase<String>(FORCED_FEEDING_POINT_ID, hashCode()) == null) {
            showFullRoad = true
        }
        //updateState { copy(feedingRouteState = FeedingRouteState.Active(showFullRoad = showFullRoad), isError = false) }
        updateState { FeedingRouteState.Active(showFullRoad = showFullRoad) }
    }

    override fun stopRoute() {
        updateState { FeedingRouteState.Disabled }
    }

    private fun updateRoute(event: RouteEvent.FeedingRouteUpdateRequest) {
        if (state is FeedingRouteState.Active) {
            updateState {
                FeedingRouteState.Active(
                    showFullRoad = showFullRoad,
                    event.result.distanceLeft,
                    state.timeLeft,
                    event.result.routeData
                )
            }
            showFullRoad = false
        }
    }

    private fun updateTimer(event: RouteEvent.FeedingTimerUpdateRequest) {
        if (state is FeedingRouteState.Active) {
            updateState {
                FeedingRouteState.Active(
                    showFullRoad = showFullRoad,
                    state.distanceLeft,
                    event.timeLeft,
                    state.routeData
                )
            }
        }
    }
}