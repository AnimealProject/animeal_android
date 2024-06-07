package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import javax.inject.Inject

class ApproveFeedingUseCase @Inject constructor(
    private val repository: FeedingRepository
) {

    suspend operator fun invoke(feedingPointId: String): ActionResult<Unit> {
        return repository.approveFeeding(feedingPointId)
    }
}