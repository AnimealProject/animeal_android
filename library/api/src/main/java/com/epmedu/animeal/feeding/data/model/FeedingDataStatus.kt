package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.FeedingStatus
import com.epmedu.animeal.feeding.data.model.FeedingDataStatus.Approved
import com.epmedu.animeal.feeding.data.model.FeedingDataStatus.InProgress
import com.epmedu.animeal.feeding.data.model.FeedingDataStatus.Pending
import com.epmedu.animeal.feeding.data.model.FeedingDataStatus.Rejected

enum class FeedingDataStatus {
    Approved,
    Rejected,
    Pending,
    InProgress
}

internal fun FeedingStatus.toFeedingDataStatus() = when (this) {
    FeedingStatus.approved -> Approved
    FeedingStatus.rejected -> Rejected
    FeedingStatus.pending -> Pending
    FeedingStatus.inProgress -> InProgress
}