package com.epmedu.animeal.feeding.data.mapper

import SearchFeedingHistoriesQuery
import com.amplifyframework.core.model.temporal.Temporal
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.users.domain.model.User

internal fun SearchFeedingHistoriesQuery.Item.toFeeding(feeder: User?): Feeding? {
    return status()?.toDomain()?.let { status ->
        Feeding(
            id = id(),
            feeder = feeder,
            status = status,
            date = Temporal.DateTime(createdAt()).toDate(),
            feedingPointId = feedingPointId()
        )
    }
}