package com.epmedu.animeal.home.presentation

import com.epmedu.animeal.home.domain.PermissionStatus

sealed interface HomeScreenEvent {
    data class FeedingPointSelected(val id: Int = -1) : HomeScreenEvent
    data class FeedingPointFavouriteChange(val id: Int = -1, val isFavourite: Boolean) : HomeScreenEvent

    object ShowWillFeedDialog : HomeScreenEvent
    object DismissWillFeedDialog : HomeScreenEvent

    data class GeolocationPermissionStatusChanged(val status: PermissionStatus) : HomeScreenEvent
}