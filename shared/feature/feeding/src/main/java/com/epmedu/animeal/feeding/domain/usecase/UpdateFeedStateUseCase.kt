package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.DomainFeedState
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import javax.inject.Inject

class UpdateFeedStateUseCase @Inject constructor(private val repository: FeedingRepository) {
    suspend operator fun invoke(newFeedState: DomainFeedState) =
        repository.updateFeedStateFlow(newFeedState)
}