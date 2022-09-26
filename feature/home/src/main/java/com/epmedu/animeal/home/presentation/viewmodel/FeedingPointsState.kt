package com.epmedu.animeal.home.presentation.viewmodel

import com.epmedu.animeal.home.presentation.model.FeedingPointUi

data class FeedingPointsState(
    val feedingPoints: List<FeedingPointUi> = emptyList()
)