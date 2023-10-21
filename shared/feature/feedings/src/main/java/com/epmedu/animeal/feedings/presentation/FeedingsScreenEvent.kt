package com.epmedu.animeal.feedings.presentation

import com.epmedu.animeal.feedings.presentation.model.FeedingItem

internal sealed interface FeedingsScreenEvent {

    val feedingItem: FeedingItem
    data class FeedingApprove(override val feedingItem: FeedingItem) : FeedingsScreenEvent
    data class FeedingReject(override val feedingItem: FeedingItem) : FeedingsScreenEvent
}