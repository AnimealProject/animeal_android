package com.epmedu.animeal.timer.domain

import com.epmedu.animeal.timer.data.repository.TimerRepository

class DisableTimerUseCase(private val repository: TimerRepository) {
    suspend operator fun invoke() = repository.disableTimer()
}