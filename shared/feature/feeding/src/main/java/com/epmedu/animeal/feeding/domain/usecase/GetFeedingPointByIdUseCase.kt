package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository

class GetFeedingPointByIdUseCase(private val repository: FeedingPointRepository) {

    operator fun invoke(id: String): FeedingPoint? = repository.getFeedingPointById(id)
}