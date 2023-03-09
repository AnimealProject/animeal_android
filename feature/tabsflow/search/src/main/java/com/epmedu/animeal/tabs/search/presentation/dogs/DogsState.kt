package com.epmedu.animeal.tabs.search.presentation.dogs

import com.epmedu.animeal.tabs.search.domain.model.GroupFeedingPointsModel

data class DogsState(
    val groupFeedingPointsModels: List<GroupFeedingPointsModel> = emptyList(),
    var query: String = "",
)
