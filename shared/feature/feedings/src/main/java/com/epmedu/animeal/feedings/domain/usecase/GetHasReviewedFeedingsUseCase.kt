package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import kotlinx.coroutines.flow.Flow

class GetHasReviewedFeedingsUseCase(
    private val repository: FeedingRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return repository.hasReviewedFeedings()
    }
}