package com.epmedu.animeal.home.presentation

import com.epmedu.animeal.home.presentation.model.FeedingPointUi

fun interface OnFeedingPointClickListener {
    fun onFeedingPointClick(feedingPointUi: FeedingPointUi)
}