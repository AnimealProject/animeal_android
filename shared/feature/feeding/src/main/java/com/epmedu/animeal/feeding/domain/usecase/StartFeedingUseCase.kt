package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import javax.inject.Inject

class StartFeedingUseCase @Inject constructor(private val repository: FeedingRepository) {

    suspend operator fun invoke(feedingPointId: String) = repository.startFeeding(feedingPointId)
}