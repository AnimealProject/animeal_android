package com.epmedu.animeal.home.presentation

internal sealed interface HomeScreenEvent {
    data class FeedingPointSelected(val id: Int = -1) : HomeScreenEvent
    data class FeedingPointFavouriteChange(val id: Int = -1, val isFavourite: Boolean) : HomeScreenEvent
}