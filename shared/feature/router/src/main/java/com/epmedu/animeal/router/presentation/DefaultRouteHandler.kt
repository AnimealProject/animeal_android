package com.epmedu.animeal.router.presentation

import com.epmedu.animeal.common.constants.Arguments.FORCED_FEEDING_POINT_ID
import com.epmedu.animeal.common.domain.usecase.ForcedArgumentsUseCase
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DefaultRouteHandler(
    stateDelegate: StateDelegate<FeedingRouteState>,
    private val forcedArgumentsUseCase: ForcedArgumentsUseCase
) : RouteHandler,
    StateDelegate<FeedingRouteState> by stateDelegate {

    override var feedingRouteStateFlow: StateFlow<FeedingRouteState> = stateFlow
    private var showFullRoad: Boolean = false
    override fun CoroutineScope.registerRouteState(updateCall: (FeedingRouteState) -> Unit) {
        launch { feedingRouteStateFlow.collectLatest { updateCall(it) } }
    }

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