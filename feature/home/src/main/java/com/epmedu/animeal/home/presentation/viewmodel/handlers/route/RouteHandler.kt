package com.epmedu.animeal.home.presentation.viewmodel.handlers.route

import com.epmedu.animeal.home.presentation.HomeScreenEvent
import kotlinx.coroutines.CoroutineScope

interface RouteHandler {
    fun handleRouteEvent(event: HomeScreenEvent.RouteEvent, scope: CoroutineScope? = null)
}