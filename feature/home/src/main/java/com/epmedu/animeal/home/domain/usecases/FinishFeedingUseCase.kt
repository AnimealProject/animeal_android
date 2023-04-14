package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import javax.inject.Inject

class FinishFeedingUseCase @Inject constructor(private val repository: FeedingRepository) {

    suspend operator fun invoke(
        feedingPointId: String,
        images: List<String>
    ): ActionResult {
        return repository.finishFeeding(feedingPointId, images)
    }
}