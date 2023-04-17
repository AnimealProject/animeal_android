package com.epmedu.animeal.feedconfirmation.domain

import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import javax.inject.Inject
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ValidateFeedingPointAvailableUseCase @Inject constructor(private val feedingPointRepository: FeedingPointRepository) {
    operator fun invoke(id: String): Flow<Boolean> {
        return feedingPointRepository.getFeedingPointsBy { it.id == id  && it.animalStatus != AnimalState.RED}
            .map { it.isEmpty() }
    }
}