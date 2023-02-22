package com.epmedu.animeal.timer.domain.usecase

import com.epmedu.animeal.timer.domain.TimerRepository

class DisableTimerUseCase(private val repository: TimerRepository) {
    suspend operator fun invoke() = repository.disableTimer()
}