package com.epmedu.animeal.home.data.model

import com.epmedu.animeal.foundation.common.FeedStatus

data class FeedingPoint(
    val id: Int,
    val title: String,
    val status: FeedStatus,
    val description: String,
    val isFavourite: Boolean,
    val lastFeeder: Feeder
)
