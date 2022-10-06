package com.epmedu.animeal.favourites.data.model

import com.epmedu.animeal.foundation.common.FeedStatus

data class FavouriteFeedingPoint(
    val id: Int = -1,
    val title: String = "",
    val status: FeedStatus = FeedStatus.GREEN,
    val isFavourite: Boolean = false,
)
