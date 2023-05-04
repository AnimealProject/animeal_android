package com.epmedu.animeal.router.presentation

import com.epmedu.animeal.router.model.RouteResult

sealed interface RouteEvent {
    data class FeedingRouteUpdateRequest(val result: RouteResult) : RouteEvent
    data class FeedingTimerUpdateRequest(val timeLeft: Long) : RouteEvent
}