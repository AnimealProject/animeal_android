package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class GetFeedStateUseCase @Inject constructor(private val repository: FeedingRepository) {
    operator fun invoke(): MutableSharedFlow<FeedState> = repository.getFeedStateFlow()
}