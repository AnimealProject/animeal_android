package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.amplifyframework.datastore.generated.model.Feeding as DataFeeding
import com.epmedu.animeal.feeding.domain.model.UserFeeding as DomainFeeding

fun DataFeeding.toDomain(isFavourite: Boolean, feedingPoint: FeedingPoint) =
    DomainFeeding(
        feedingPoint = feedingPoint.toDomainFeedingPoint(isFavourite),
        createdAt = createdAt.toDate()
    )