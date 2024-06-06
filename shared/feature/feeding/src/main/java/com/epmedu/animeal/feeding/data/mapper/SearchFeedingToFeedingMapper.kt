package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.core.model.temporal.Temporal
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.model.User

internal suspend fun SearchFeedingsQuery.Item.toFeeding(
    feeder: User?,
    getImageFrom: suspend (fileName: String) -> NetworkFile
) = Feeding(
    id = id(),
    feeder = feeder,
    status = status().toDomain(),
    date = Temporal.DateTime(createdAt()).toDate(),
    feedingPointId = feedingPointFeedingsId(),
    photos = images().map { getImageFrom(it) }
)