package com.epmedu.animeal.home.presentation

import com.epmedu.animeal.home.presentation.model.RouteResult

sealed interface HomeScreenEvent {
    data class FeedingPointSelected(val id: Int = -1) : HomeScreenEvent
    data class FeedingPointFavouriteChange(val id: Int = -1, val isFavourite: Boolean) :
        HomeScreenEvent

    data class FeedingRouteUpdateRequest(val result: RouteResult) : HomeScreenEvent
    data class FeedingTimerUpdateRequest(val timeLeft: Long) : HomeScreenEvent

    object FeedingRouteStartRequest : HomeScreenEvent
    object FeedingRouteCancellationRequest : HomeScreenEvent
    object UserCurrentGeolocationRequest : HomeScreenEvent
    object ShowWillFeedDialog : HomeScreenEvent
    object DismissWillFeedDialog : HomeScreenEvent
}