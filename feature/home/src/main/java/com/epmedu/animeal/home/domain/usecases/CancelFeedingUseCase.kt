package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import javax.inject.Inject

class CancelFeedingUseCase @Inject constructor(private val repository: FeedingRepository) {

    suspend operator fun invoke(feedingPointId: String) = repository.cancelFeeding(feedingPointId)
}