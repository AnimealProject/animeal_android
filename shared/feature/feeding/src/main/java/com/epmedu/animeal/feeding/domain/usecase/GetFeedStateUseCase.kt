package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetFeedStateUseCase @Inject constructor(private val feedingRepository: FeedingRepository) {

    operator fun invoke() = feedingRepository.getFeedStateFlow().distinctUntilChanged()
}