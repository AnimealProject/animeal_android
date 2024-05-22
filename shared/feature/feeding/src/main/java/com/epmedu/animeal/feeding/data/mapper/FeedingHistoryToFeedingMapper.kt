package com.epmedu.animeal.feeding.data.mapper

import OnCreateFeedingHistoryExtSubscription
import SearchFeedingHistoriesQuery
import com.amplifyframework.core.model.temporal.Temporal
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.model.User

internal suspend fun SearchFeedingHistoriesQuery.Item.toFeeding(
    feeder: User?,
    reviewedBy: User?,
    rejectionReason: String?,
    getImageFrom: suspend (fileName: String) -> NetworkFile
): Feeding? {
    return status()?.toDomain()?.let { status ->
        Feeding(
            id = id(),
            feeder = feeder,
            status = status,
            date = Temporal.DateTime(createdAt()).toDate(),
            feedingPointId = feedingPointId(),
            photos = images().map { getImageFrom(it) },
            reviewedBy = reviewedBy,
            rejectionReason = rejectionReason
        )
    }
}

internal suspend fun OnCreateFeedingHistoryExtSubscription.OnCreateFeedingHistoryExt.toFeeding(
    getImageFrom: suspend (fileName: String) -> NetworkFile,
    reviewedBy: User?,
    feeder: User? = null
): Feeding? {
    return status()?.toDomain()?.let { status ->
        Feeding(
            id = id(),
            feeder = feeder,
            status = status,
            date = Temporal.DateTime(createdAt()).toDate(),
            feedingPointId = feedingPointId(),
            photos = images().map { getImageFrom(it) },
            reviewedBy = reviewedBy
        )
    }
}