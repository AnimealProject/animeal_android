package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import javax.inject.Inject

class RejectFeedingUseCase @Inject constructor(private val repository: FeedingPointRepository) {

    suspend operator fun invoke(feedingPointId: String, reason: String) =
        repository.rejectFeeding(feedingPointId, reason)
}