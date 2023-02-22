package com.epmedu.animeal.timer.domain.usecase

import com.epmedu.animeal.timer.domain.TimerRepository

class GetTimerStateUseCase(private val repository: TimerRepository) {
    operator fun invoke() = repository.getTimerState()
}