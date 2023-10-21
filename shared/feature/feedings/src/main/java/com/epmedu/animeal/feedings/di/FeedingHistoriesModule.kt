package com.epmedu.animeal.feedings.di

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.feedings.domain.usecase.GetFeedingHistoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object FeedingHistoriesModule {

    @ViewModelScoped
    @Provides
    fun provideGetFeedingHistoriesUseCase(
        feedingRepository: FeedingRepository
    ): GetFeedingHistoriesUseCase =
        GetFeedingHistoriesUseCase(feedingRepository)
}