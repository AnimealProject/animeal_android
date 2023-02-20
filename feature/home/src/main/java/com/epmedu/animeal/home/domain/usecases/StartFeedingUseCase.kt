package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import javax.inject.Inject

class StartFeedingUseCase @Inject constructor(private val repository: FeedingPointRepository) {

    suspend operator fun invoke(feedingPointId: String) = repository.startFeeding(feedingPointId)
}