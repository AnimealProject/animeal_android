package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import javax.inject.Inject

class GetAllFeedingPointsUseCase @Inject constructor(private val repository: FeedingPointRepository) {

    operator fun invoke() = repository.getAllFeedingPoints()
}