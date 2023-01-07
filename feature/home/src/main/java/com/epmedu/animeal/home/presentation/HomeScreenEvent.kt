package com.epmedu.animeal.home.presentation

import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.home.domain.PermissionStatus

sealed interface HomeScreenEvent {
    data class FeedingPointSelected(val id: String = EMPTY_STRING) : HomeScreenEvent
    data class FeedingPointFavouriteChange(val id: String = EMPTY_STRING, val isFavourite: Boolean) : HomeScreenEvent

    object ShowWillFeedDialog : HomeScreenEvent
    object DismissWillFeedDialog : HomeScreenEvent

    data class GeolocationPermissionStatusChanged(val status: PermissionStatus) : HomeScreenEvent
}