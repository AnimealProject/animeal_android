package com.epmedu.animeal.home.presentation

import com.epmedu.animeal.home.presentation.model.RouteResult

sealed interface HomeScreenEvent {
    data class FeedingPointSelected(val id: Int = -1) : HomeScreenEvent
    data class FeedingPointFavouriteChange(val id: Int = -1, val isFavourite: Boolean) :
        HomeScreenEvent

    sealed interface RouteEvent : HomeScreenEvent {
        object FeedingRouteStartRequest : RouteEvent
        object FeedingRouteCancellationRequest : RouteEvent
        data class FeedingRouteUpdateRequest(val result: RouteResult) : RouteEvent
        data class FeedingTimerUpdateRequest(val timeLeft: Long) : RouteEvent
    }

    sealed interface WillFeedEvent : HomeScreenEvent {
        object ShowWillFeedDialog : WillFeedEvent
        object DismissWillFeedDialog : WillFeedEvent
    }

    object UserCurrentGeolocationRequest : HomeScreenEvent
}