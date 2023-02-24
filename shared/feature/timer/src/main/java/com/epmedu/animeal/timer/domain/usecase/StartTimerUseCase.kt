package com.epmedu.animeal.timer.domain.usecase

import com.epmedu.animeal.timer.domain.TimerRepository

class StartTimerUseCase(private val repository: TimerRepository) {
    operator fun invoke(timeInMillis: Long, intervalInMillis: Long) =
        repository.startTimer(timeInMillis, intervalInMillis)
}