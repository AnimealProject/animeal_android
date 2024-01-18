package com.epmedu.animeal.feeding.data.mapper

import com.epmedu.animeal.feeding.domain.model.FeedingStatus as DomainFeedingStatus
import type.FeedingStatus as DataFeedingStatus

fun DataFeedingStatus.toDomain(): DomainFeedingStatus = when (this) {
    DataFeedingStatus.inProgress -> DomainFeedingStatus.InProgress
    DataFeedingStatus.pending -> DomainFeedingStatus.Pending
    DataFeedingStatus.approved -> DomainFeedingStatus.Approved
    DataFeedingStatus.rejected -> DomainFeedingStatus.Rejected
    DataFeedingStatus.outdated -> DomainFeedingStatus.Outdated
}