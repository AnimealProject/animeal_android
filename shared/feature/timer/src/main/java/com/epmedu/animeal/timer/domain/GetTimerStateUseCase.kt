package com.epmedu.animeal.timer.domain

import com.epmedu.animeal.timer.data.repository.TimerRepository

class GetTimerStateUseCase(private val repository: TimerRepository) {
    operator fun invoke() = repository.getTimerState()
}