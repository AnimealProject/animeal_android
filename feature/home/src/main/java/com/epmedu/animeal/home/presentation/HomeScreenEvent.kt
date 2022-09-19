package com.epmedu.animeal.home.presentation

internal sealed interface HomeScreenEvent {
    class FeedSpotSelected(val id: Int = -1) : HomeScreenEvent
    class FeedSpotFavouriteChange(val id: Int = -1, val isFavourite: Boolean) : HomeScreenEvent
}