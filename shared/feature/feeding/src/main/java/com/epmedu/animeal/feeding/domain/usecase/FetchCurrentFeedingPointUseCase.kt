package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.extensions.HOUR_IN_MILLIS
import com.epmedu.animeal.extensions.MINUTE_IN_MILLIS
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.UserFeeding
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.timer.data.model.TimerState
import com.epmedu.animeal.timer.domain.usecase.GetTimerStateUseCase
import com.epmedu.animeal.timer.domain.usecase.StartTimerUseCase

class FetchCurrentFeedingPointUseCase(
    private val getTimerStateUseCase: GetTimerStateUseCase,
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

    private fun fetchFeedingPoint(userFeedings: List<UserFeeding>): FeedingPoint? {
        val latestFeeding = userFeedings.maxBy { it.createdAt }
        val millisPassed = System.currentTimeMillis() - latestFeeding.createdAt.time

        return when {
            millisPassed < HOUR_IN_MILLIS -> {
                startTimer(millisPassed)
                latestFeeding.feedingPoint
            }
            else -> {
                null
            }
        }
    }

    private fun startTimer(millisPassed: Long) {
        if (getTimerStateUseCase().value !is TimerState.Active) {
            startTimerUseCase(HOUR_IN_MILLIS - millisPassed, MINUTE_IN_MILLIS)
        }
    }
}