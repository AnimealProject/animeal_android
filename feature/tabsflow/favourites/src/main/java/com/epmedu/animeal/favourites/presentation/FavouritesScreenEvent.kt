package com.epmedu.animeal.favourites.presentation

import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING

internal sealed class FavouritesScreenEvent(open val id: String = EMPTY_STRING) {
    data class FeedSpotSelected(override val id: String) : FavouritesScreenEvent(id)
    object FeedingPointSheetHidden : FavouritesScreenEvent("0")
    data class FeedSpotChanged(override val id: String, val isFavorite: Boolean) :
        FavouritesScreenEvent(id)
    data class ShowWillFeedDialog(override val id: String) : FavouritesScreenEvent(id)
    object DismissWillFeedDialog : FavouritesScreenEvent("0")
}