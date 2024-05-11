package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetHasReviewedFeedingsUseCase(
    private val feedingRepository: FeedingRepository,
    private val networkRepository: NetworkRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return feedingRepository.getAllFeedings().map { feedings ->
            val currentUserId = networkRepository.getUserId()

            feedings.any { it.reviewedBy?.id == currentUserId }
        }
    }
}