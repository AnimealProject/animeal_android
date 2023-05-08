package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import javax.inject.Inject

class RejectFeedingUseCase @Inject constructor(private val repository: FeedingRepository) {

    suspend operator fun invoke(feedingPointId: String, reason: String) =
        repository.rejectFeeding(feedingPointId, reason)
}