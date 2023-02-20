package com.epmedu.animeal.timer.di

import com.epmedu.animeal.timer.data.repository.TimerRepository
import com.epmedu.animeal.timer.data.repository.TimerRepositoryImpl
import com.epmedu.animeal.timer.domain.DisableTimerUseCase
import com.epmedu.animeal.timer.domain.GetTimerStateUseCase
import com.epmedu.animeal.timer.domain.StartTimerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimerModule {

    @Singleton
    @Provides
    fun provideProfileRepository(): TimerRepository = TimerRepositoryImpl()

    @Singleton
    @Provides
    fun provideGetTimerStateUseCase(
        repository: TimerRepository
    ) = GetTimerStateUseCase(repository)

    @Singleton
    @Provides
    fun provideStartTimerUseCase(
        repository: TimerRepository
    ) = StartTimerUseCase(repository)

    @Singleton
    @Provides
    fun provideAcceptTimerExpirationUseCase(
        repository: TimerRepository
    ) = DisableTimerUseCase(repository)
}