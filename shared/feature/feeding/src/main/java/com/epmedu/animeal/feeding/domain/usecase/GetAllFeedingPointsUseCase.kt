package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFeedingPointsUseCase @Inject constructor(private val repository: FeedingPointRepository) {

    operator fun invoke(type: AnimalType): Flow<List<FeedingPoint>> =
        repository.getFeedingPointsBy {
            it.animalType == type
        }
}