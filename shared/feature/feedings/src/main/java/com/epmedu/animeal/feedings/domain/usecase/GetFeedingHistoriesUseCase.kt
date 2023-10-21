package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFeedingHistoriesUseCase(private val repository: FeedingRepository) {

    operator fun invoke(feedingPointId: String, status: String): Flow<List<FeedingHistory>> {
        return repository.getFeedingHistories(feedingPointId, status).map { feedingHistories ->
            feedingHistories.sortedByDescending { history -> history.date }
        }
    }
}