package com.epmedu.animeal.router.presentation

import kotlinx.coroutines.flow.StateFlow

interface RouteHandler {

    var feedingRouteStateFlow: StateFlow<FeedingRouteState>

    fun handleRouteEvent(event: RouteEvent)

    fun startRoute()

    fun stopRoute()
}