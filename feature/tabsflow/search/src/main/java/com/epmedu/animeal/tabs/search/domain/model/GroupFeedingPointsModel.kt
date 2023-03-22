package com.epmedu.animeal.tabs.search.domain.model

import com.epmedu.animeal.feeding.domain.model.FeedingPoint

data class GroupFeedingPointsModel(
    val title: String,
    val points: List<FeedingPoint>
)