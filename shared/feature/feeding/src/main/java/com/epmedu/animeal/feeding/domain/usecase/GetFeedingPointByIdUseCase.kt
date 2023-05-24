package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository

class GetFeedingPointByIdUseCase(private val repository: FeedingPointRepository) {

    operator fun invoke(id: String) = repository.getFeedingPointById(id)
}