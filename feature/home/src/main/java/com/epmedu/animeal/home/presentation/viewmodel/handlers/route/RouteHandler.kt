package com.epmedu.animeal.home.presentation.viewmodel.handlers.route

import com.epmedu.animeal.home.presentation.HomeScreenEvent

interface RouteHandler {
    fun handleRouteEvent(event: HomeScreenEvent.RouteEvent)
}