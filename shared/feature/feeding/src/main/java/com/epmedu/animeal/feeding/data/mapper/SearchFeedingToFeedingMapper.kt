package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.core.model.temporal.Temporal
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.users.domain.model.User

internal fun SearchFeedingsQuery.Item.toFeeding(feeder: User?) = Feeding(
    id = id(),
    name = feeder?.name.orEmpty(),
    surname = feeder?.surname.orEmpty(),
    status = status().toDomain(),
    date = Temporal.DateTime(createdAt()).toDate(),
    feedingPointId = feedingPointFeedingsId()
)