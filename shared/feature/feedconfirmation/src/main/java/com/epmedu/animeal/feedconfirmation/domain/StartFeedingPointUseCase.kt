package com.epmedu.animeal.feedconfirmation.domain

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import javax.inject.Inject

class StartFeedingPointUseCase @Inject constructor(private val feedingPointRepository: FeedingPointRepository) {
    suspend operator fun invoke(feedingPointId: String) = feedingPointRepository.startFeeding(feedingPointId)
}