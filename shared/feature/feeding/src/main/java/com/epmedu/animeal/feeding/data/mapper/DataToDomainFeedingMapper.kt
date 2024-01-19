package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.amplifyframework.datastore.generated.model.Feeding as DataFeeding
import com.epmedu.animeal.feeding.domain.model.UserFeeding as DomainFeeding

suspend fun DataFeeding.toDomain(
    getImageFromName: suspend (String) -> NetworkFile,
    isFavourite: Boolean,
    feedingPoint: FeedingPoint
) = DomainFeeding(
    feedingPoint = feedingPoint.toDomainFeedingPoint(getImageFromName, isFavourite),
    createdAt = createdAt.toDate()
)