package com.epmedu.animeal.home.presentation

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.model.RouteResult

sealed interface HomeScreenEvent {

    sealed interface FeedingPointEvent : HomeScreenEvent {
        data class Select(val feedingPoint: FeedingPointModel) : FeedingPointEvent
        data class FavouriteChange(val isFavourite: Boolean) : FeedingPointEvent
    }

    sealed interface FeedingEvent : HomeScreenEvent {
        object Start : FeedingEvent
        object Cancel : FeedingEvent
        object Finish : FeedingEvent
    }

    sealed interface RouteEvent : HomeScreenEvent {
        data class FeedingRouteUpdateRequest(val result: RouteResult) : RouteEvent
        data class FeedingTimerUpdateRequest(val timeLeft: Long) : RouteEvent
    }

    sealed interface WillFeedEvent : HomeScreenEvent {
        object ShowWillFeedDialog : WillFeedEvent
        object DismissWillFeedDialog : WillFeedEvent
    }

    data class GeolocationPermissionStatusChanged(val status: PermissionStatus) : HomeScreenEvent

    object ErrorShowed : HomeScreenEvent
}