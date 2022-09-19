package com.epmedu.animeal.home.presentation

internal sealed interface HomeScreenEvent {
    class FeedingPointSelected(val id: Int = -1) : HomeScreenEvent
    class FeedingPointFavouriteChange(val id: Int = -1, val isFavourite: Boolean) : HomeScreenEvent
}