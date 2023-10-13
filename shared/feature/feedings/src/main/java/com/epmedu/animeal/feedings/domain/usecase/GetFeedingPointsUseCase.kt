package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository

class GetFeedingPointsUseCase(private val feedingPointRepository: FeedingPointRepository) {

    operator fun invoke() = feedingPointRepository.getAllFeedingPoints()
}