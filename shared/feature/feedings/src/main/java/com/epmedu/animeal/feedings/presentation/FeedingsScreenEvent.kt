package com.epmedu.animeal.feedings.presentation

internal sealed interface FeedingsScreenEvent {

    data class FeedingApprove(val feedingId: String) : FeedingsScreenEvent
    data class FeedingReject(val feedingId: String) : FeedingsScreenEvent
}