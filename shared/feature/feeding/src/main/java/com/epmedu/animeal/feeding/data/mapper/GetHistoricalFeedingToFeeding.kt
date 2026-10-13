package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.core.model.temporal.Temporal
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.model.User

internal suspend fun GetHistoricalFeedingsQuery.GetHistoricalFeeding.toFeeding(
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
            statusUpdated = if (!moderatedAt().isNullOrEmpty()) {
                Temporal.DateTime(moderatedAt()!!).toDate()
            } else {
                Temporal.DateTime(updatedAt()).toDate()
            },
            feedingPointId = feedingPointId(),
            photos = images().map { getImageFrom(it) },
            reviewedBy = reviewedBy,
            rejectionReason = rejectionReason
        )
    }
}