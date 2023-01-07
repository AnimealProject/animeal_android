package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.FeedingPointStatus
import com.epmedu.animeal.feeding.data.model.FeedingPointDataStatus.Fed
import com.epmedu.animeal.feeding.data.model.FeedingPointDataStatus.Pending
import com.epmedu.animeal.feeding.data.model.FeedingPointDataStatus.Starved

enum class FeedingPointDataStatus {
    Fed,
    Pending,
    Starved
}

internal fun FeedingPointStatus.toFeedingPointDataStatus() = when (this) {
    FeedingPointStatus.fed -> Fed
    FeedingPointStatus.pending -> Pending
    FeedingPointStatus.starved -> Starved
}
