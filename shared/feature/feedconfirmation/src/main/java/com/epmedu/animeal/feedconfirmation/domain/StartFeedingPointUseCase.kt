package com.epmedu.animeal.feedconfirmation.domain

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import javax.inject.Inject

class StartFeedingPointUseCase @Inject constructor(private val feedingRepository: FeedingRepository) {
    suspend operator fun invoke(feedingPointId: String) = feedingRepository.startFeeding(feedingPointId)
}