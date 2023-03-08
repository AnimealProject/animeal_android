package com.epmedu.animeal.tabs.search.presentation.model

import androidx.compose.runtime.Stable
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel

@Stable
data class GroupFeedingPointsModel(
    var isExpanded: Boolean = true,
    val title: String,
    val points: List<FeedingPointModel>
)