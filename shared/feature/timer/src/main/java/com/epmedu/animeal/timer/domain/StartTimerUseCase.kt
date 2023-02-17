package com.epmedu.animeal.timer.domain

import com.epmedu.animeal.timer.data.repository.TimerRepository

class StartTimerUseCase(private val repository: TimerRepository) {
    operator fun invoke(timeInMillis: Long, intervalInMillis: Long) =
        repository.startTimer(timeInMillis, intervalInMillis)
}