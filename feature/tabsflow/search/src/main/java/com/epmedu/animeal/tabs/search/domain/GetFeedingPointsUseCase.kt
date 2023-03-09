package com.epmedu.animeal.tabs.search.domain

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import kotlinx.coroutines.flow.map

class GetFeedingPointsUseCase(private val feedingPointRepository: FeedingPointRepository) {
    operator fun invoke() = feedingPointRepository.getAllFeedingPoints()
        .map { feedingPoints -> feedingPoints.filter { it.animalType == AnimalType.Dogs } }
}
