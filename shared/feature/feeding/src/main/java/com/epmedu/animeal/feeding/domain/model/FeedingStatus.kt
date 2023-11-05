package com.epmedu.animeal.feeding.domain.model

enum class FeedingStatus {
    APPROVED,
    REJECTED,
    PENDING,
    IN_PROGRESS,
    OUTDATED
}
fun toFeedingStatus(feedingStatus: type.FeedingStatus): FeedingStatus =
    when (feedingStatus) {
        type.FeedingStatus.outdated -> FeedingStatus.OUTDATED
        type.FeedingStatus.approved -> FeedingStatus.APPROVED
        type.FeedingStatus.rejected -> FeedingStatus.REJECTED
        type.FeedingStatus.pending -> FeedingStatus.PENDING
        type.FeedingStatus.inProgress -> FeedingStatus.IN_PROGRESS
    }
