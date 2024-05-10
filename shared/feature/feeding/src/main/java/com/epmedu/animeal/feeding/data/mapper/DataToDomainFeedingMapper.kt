package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.model.User
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

internal suspend fun OnCreateFeedingExtSubscription.OnCreateFeedingExt.toFeeding(
    getImageFrom: suspend (fileName: String) -> NetworkFile,
    feeder: User? = null
): Feeding {
    return Feeding(
        id = id(),
        feeder = feeder,
        status = status().toDomain(),
        date = Temporal.DateTime(createdAt()).toDate(),
        feedingPointId = feedingPointFeedingsId(),
        photos = images().map { getImageFrom(it) }
    )
}

internal suspend fun OnUpdateFeedingExtSubscription.OnUpdateFeedingExt.toFeeding(
    getImageFrom: suspend (fileName: String) -> NetworkFile,
    feeder: User? = null
): Feeding {
    return Feeding(
        id = id(),
        feeder = feeder,
        status = status().toDomain(),
        date = Temporal.DateTime(createdAt()).toDate(),
        feedingPointId = feedingPointFeedingsId(),
        photos = images().map { getImageFrom(it) }
    )
}