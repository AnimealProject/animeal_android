package com.epmedu.animeal.favourites.presentation

internal sealed class FavouritesScreenEvent(open val id: Int = -1) {
    data class FeedSpotSelected(override val id: Int) : FavouritesScreenEvent(id)
    data class FeedSpotChanged(override val id: Int, val isFavorite: Boolean) :
        FavouritesScreenEvent(id)
    data class ShowWillFeedDialog(override val id: Int) : FavouritesScreenEvent(id)
    object DismissWillFeedDialog : FavouritesScreenEvent(0)
}