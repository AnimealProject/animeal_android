package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.datastore.generated.model.FeedingStatus as AmplifyFeedingStatus
import com.epmedu.animeal.feeding.domain.model.FeedingStatus as DomainFeedingStatus
import type.FeedingStatus as ApolloFeedingStatus

fun ApolloFeedingStatus.toDomain(): DomainFeedingStatus = when (this) {
    ApolloFeedingStatus.inProgress -> DomainFeedingStatus.InProgress
    ApolloFeedingStatus.pending -> DomainFeedingStatus.Pending
    ApolloFeedingStatus.approved -> DomainFeedingStatus.Approved
    ApolloFeedingStatus.rejected -> DomainFeedingStatus.Rejected
    ApolloFeedingStatus.outdated -> DomainFeedingStatus.Outdated
}

fun AmplifyFeedingStatus.toDomain(): DomainFeedingStatus = when (this) {
    AmplifyFeedingStatus.inProgress -> DomainFeedingStatus.InProgress
    AmplifyFeedingStatus.pending -> DomainFeedingStatus.Pending
    AmplifyFeedingStatus.approved -> DomainFeedingStatus.Approved
    AmplifyFeedingStatus.rejected -> DomainFeedingStatus.Rejected
    AmplifyFeedingStatus.outdated -> DomainFeedingStatus.Outdated
}