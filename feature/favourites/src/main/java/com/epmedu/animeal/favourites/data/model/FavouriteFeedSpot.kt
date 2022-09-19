package com.epmedu.animeal.favourites.data.model

import com.epmedu.animeal.foundation.common.FeedStatus

data class FavouriteFeedSpot(
    val id: Int = -1,
    val title: String = "",
    val status: FeedStatus = FeedStatus.ORANGE,
    val isFavourite: Boolean = false,
)
