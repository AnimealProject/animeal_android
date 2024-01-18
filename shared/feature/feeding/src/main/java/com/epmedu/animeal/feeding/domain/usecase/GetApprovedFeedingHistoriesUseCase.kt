package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.feeding.domain.model.FeedingStatus
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetApprovedFeedingHistoriesUseCase(private val repository: FeedingRepository) {

    operator fun invoke(feedingPointId: String): Flow<List<FeedingHistory>> {
        return repository.getFeedingHistoriesBy(
            feedingPointId = feedingPointId,
            status = FeedingStatus.Approved
        ).map { feedingHistories ->
            feedingHistories.sortedByDescending { history -> history.date }
        }
    }
}