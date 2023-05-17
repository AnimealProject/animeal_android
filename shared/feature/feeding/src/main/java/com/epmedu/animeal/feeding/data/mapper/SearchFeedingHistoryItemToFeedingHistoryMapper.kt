package com.epmedu.animeal.feeding.data.mapper

import SearchFeedingHistoriesQuery
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.FeedingHistory

internal fun SearchFeedingHistoriesQuery.Item.toFeedingHistory(): FeedingHistory {
    return FeedingHistory
        .builder()
        .userId(userId())
        .images(images())
        .createdAt(Temporal.DateTime(createdAt()))
        .updatedAt(Temporal.DateTime(updatedAt()))
        .feedingPointId(feedingPointId())
        .id(id())
        .build()
}