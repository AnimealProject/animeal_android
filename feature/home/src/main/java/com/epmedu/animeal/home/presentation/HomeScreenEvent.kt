package com.epmedu.animeal.home.presentation

import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.model.RouteResult

sealed interface HomeScreenEvent {
    data class FeedingPointSelected(val id: String = EMPTY_STRING) : HomeScreenEvent
    data class FeedingPointFavouriteChange(val id: String = EMPTY_STRING, val isFavourite: Boolean) : HomeScreenEvent

    sealed interface RouteEvent : HomeScreenEvent {
        data class FeedingRouteStartRequest(val onTimerExpire: () -> Unit) : RouteEvent
        object FeedingRouteCancellationRequest : RouteEvent
        data class FeedingRouteUpdateRequest(val result: RouteResult) : RouteEvent
        data class FeedingTimerUpdateRequest(val timeLeft: Long) : RouteEvent
    }

    sealed interface WillFeedEvent : HomeScreenEvent {
        object ShowWillFeedDialog : WillFeedEvent
        object DismissWillFeedDialog : WillFeedEvent
    }

    data class GeolocationPermissionStatusChanged(val status: PermissionStatus) : HomeScreenEvent
}