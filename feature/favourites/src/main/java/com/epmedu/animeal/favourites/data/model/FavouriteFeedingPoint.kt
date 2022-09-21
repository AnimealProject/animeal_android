package com.epmedu.animeal.favourites.data.model

import com.epmedu.animeal.foundation.common.FeedStatus

data class FavouriteFeedingPoint(
    val id: Int ,
    val title: String ,
    val status: FeedStatus ,
    val isFavourite: Boolean = false,
)
