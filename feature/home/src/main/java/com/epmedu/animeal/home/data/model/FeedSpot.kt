package com.epmedu.animeal.home.data.model

data class FeedSpot(
    val id: Int = -1,
    val title: String = "",
    val status: FeedStatus = FeedStatus.ORANGE,
    val description: String = "",
    val isFavourite: Boolean = false,
    val lastFeeder: Feeder = Feeder(),
)
