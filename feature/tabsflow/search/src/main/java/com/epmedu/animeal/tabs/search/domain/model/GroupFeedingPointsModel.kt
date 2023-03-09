package com.epmedu.animeal.tabs.search.domain.model

import androidx.compose.runtime.Stable
import com.epmedu.animeal.feeding.domain.model.FeedingPoint

@Stable
data class GroupFeedingPointsModel(
    var isExpanded: Boolean = true,
    val title: String,
    val points: List<FeedingPoint>
)