package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import javax.inject.Inject

class ExpireFeedingUseCase @Inject constructor(
    private val repository: FeedingRepository
) {

    suspend operator fun invoke(feedingPointId: String): ActionResult<Unit> {
        return repository.expireFeeding(feedingPointId)
    }
}