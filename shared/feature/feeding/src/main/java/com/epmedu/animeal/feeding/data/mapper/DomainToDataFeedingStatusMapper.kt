package com.epmedu.animeal.feeding.data.mapper

import com.epmedu.animeal.feeding.domain.model.FeedingStatus as DomainFeedingStatus
import type.FeedingStatus as DataFeedingStatus

fun DomainFeedingStatus.toData(): DataFeedingStatus = when (this) {
    DomainFeedingStatus.InProgress -> DataFeedingStatus.inProgress
    DomainFeedingStatus.Pending -> DataFeedingStatus.pending
    DomainFeedingStatus.Outdated -> DataFeedingStatus.outdated
    DomainFeedingStatus.Approved -> DataFeedingStatus.approved
    DomainFeedingStatus.Rejected -> DataFeedingStatus.rejected
}