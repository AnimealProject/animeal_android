package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import javax.inject.Inject

class CancelFeedingUseCase @Inject constructor(private val repository: FeedingPointRepository) {

    suspend operator fun invoke(feedingPointId: String) = repository.cancelFeeding(feedingPointId)
}