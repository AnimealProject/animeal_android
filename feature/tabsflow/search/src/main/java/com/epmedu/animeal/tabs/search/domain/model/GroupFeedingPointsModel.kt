package com.epmedu.animeal.tabs.search.domain.model

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel

data class GroupFeedingPointsModel(
    val title: String,
    val points: List<FeedingPointModel>
)