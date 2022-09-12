package com.epmedu.animeal.home.presentation

internal sealed interface HomeScreenEvent {
    data class FeedSpotSelected(val id: Int = -1) : HomeScreenEvent
    data class FeedSpotFavouriteChange(val id: Int = -1, val isFavourite: Boolean) : HomeScreenEvent
}