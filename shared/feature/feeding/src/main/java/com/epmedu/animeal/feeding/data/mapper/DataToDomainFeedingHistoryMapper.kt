package com.epmedu.animeal.feeding.data.mapper

import SearchFeedingHistoriesQuery
import com.amplifyframework.core.model.temporal.Temporal
import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.users.domain.model.User

internal fun SearchFeedingHistoriesQuery.Item.toDomain(feeder: User?) = FeedingHistory(
    id = id(),
    name = feeder?.name.orEmpty(),
    surname = feeder?.surname.orEmpty(),
    date = Temporal.DateTime(createdAt()).toDate()
)