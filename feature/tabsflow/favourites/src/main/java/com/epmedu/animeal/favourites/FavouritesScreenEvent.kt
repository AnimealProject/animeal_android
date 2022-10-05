package com.epmedu.animeal.favourites

internal sealed class FavouritesScreenEvent(open val id: Int = -1) {
    data class FeedSpotSelected(override val id: Int) : FavouritesScreenEvent(id)
    data class FeedSpotRemove(override val id: Int) : FavouritesScreenEvent(id)
}