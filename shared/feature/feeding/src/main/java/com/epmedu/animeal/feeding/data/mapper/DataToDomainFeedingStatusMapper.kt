package com.epmedu.animeal.feeding.data.mapper

import com.epmedu.animeal.feeding.domain.model.FeedingStatus as DomainFeedingStatus
import type.FeedingStatus as ApolloFeedingStatus

fun ApolloFeedingStatus.toDomain(): DomainFeedingStatus = when (this) {
    ApolloFeedingStatus.inProgress -> DomainFeedingStatus.InProgress
    ApolloFeedingStatus.pending -> DomainFeedingStatus.Pending
    ApolloFeedingStatus.approved -> DomainFeedingStatus.Approved
    ApolloFeedingStatus.rejected -> DomainFeedingStatus.Rejected
    ApolloFeedingStatus.outdated -> DomainFeedingStatus.Outdated
}