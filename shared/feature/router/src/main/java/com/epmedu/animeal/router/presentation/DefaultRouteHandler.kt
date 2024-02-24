package com.epmedu.animeal.router.presentation

import com.epmedu.animeal.common.constants.Arguments.FORCED_FEEDING_POINT_ID
import com.epmedu.animeal.common.domain.usecase.ForcedArgumentsUseCase
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import kotlinx.coroutines.flow.StateFlow

class DefaultRouteHandler(
    stateDelegate: StateDelegate<FeedingRouteState>,
    private val forcedArgumentsUseCase: ForcedArgumentsUseCase
) : RouteHandler,
    StateDelegate<FeedingRouteState> by stateDelegate {

    override var feedingRouteStateFlow: StateFlow<FeedingRouteState> = stateFlow
    private var showFullRoad: Boolean = false

    override fun handleRouteEvent(event: RouteEvent) {
        when (event) {
            is RouteEvent.FeedingRouteUpdateRequest -> updateRoute(event)
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
                    distanceLeft = event.result.distanceLeft,
                    routeData = event.result.routeData
                )
            }
            showFullRoad = false
        }
    }
}