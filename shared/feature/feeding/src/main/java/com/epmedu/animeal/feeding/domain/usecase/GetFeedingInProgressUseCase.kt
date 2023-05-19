package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.FeedingInProgress
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import kotlinx.coroutines.flow.Flow

class GetFeedingInProgressUseCase(private val feedingRepository: FeedingRepository) {

    operator fun invoke(feedingPointId: String): Flow<FeedingInProgress?> {
        return feedingRepository.getFeedingInProgress(feedingPointId)
    }
}