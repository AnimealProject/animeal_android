package com.epmedu.animeal.router.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface RouteHandler {

    var feedingRouteStateFlow: StateFlow<FeedingRouteState>
    fun CoroutineScope.registerRouteState(updateCall: (FeedingRouteState) -> Unit)

    fun handleRouteEvent(event: RouteEvent)

    fun startRoute()

    fun stopRoute()
}