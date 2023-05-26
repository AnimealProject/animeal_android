package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.DomainFeedState
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class GetFeedStateUseCase @Inject constructor(private val repository: FeedingRepository) {
    operator fun invoke(): MutableSharedFlow<DomainFeedState> = repository.getFeedStateFlow()
}