package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository

class FetchFeedingPointByIdUseCase(
    private val repository: FeedingPointRepository
) {
    suspend operator fun invoke(id: String): FeedingPoint {
        return repository.getFeedingPointById(id)
    }
}