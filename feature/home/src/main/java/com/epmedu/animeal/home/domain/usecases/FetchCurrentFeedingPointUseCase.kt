package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.extensions.HOUR_IN_MILLIS
import com.epmedu.animeal.extensions.MINUTE_IN_MILLIS
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.timer.domain.usecase.StartTimerUseCase

class FetchCurrentFeedingPointUseCase(
    private val startTimerUseCase: StartTimerUseCase,
    private val repository: FeedingRepository
) {

    suspend operator fun invoke(): FeedingPoint? {
        val feedings = repository.getUserFeedings()
        return when {
            feedings.isEmpty() -> null
            else -> fetchFeedingPoint(feedings)
        }
    }

    private fun fetchFeedingPoint(feedings: List<Feeding>): FeedingPoint? {
        val latestFeeding = feedings.maxBy { it.createdAt }
        val millisPassed = System.currentTimeMillis() - latestFeeding.createdAt.time

        return when {
            millisPassed < HOUR_IN_MILLIS -> {
                startTimerUseCase(HOUR_IN_MILLIS - millisPassed, MINUTE_IN_MILLIS)
                latestFeeding.feedingPoint
            }
            else -> {
                null
            }
        }
    }
}