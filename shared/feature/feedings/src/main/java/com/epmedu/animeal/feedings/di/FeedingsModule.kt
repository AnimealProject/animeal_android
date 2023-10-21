package com.epmedu.animeal.feedings.di

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feedings.domain.usecase.GetFeedingPointsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object FeedingsModule {

    @ViewModelScoped
    @Provides
    fun provideGetFeedingPointsUseCase(
        feedingPointRepository: FeedingPointRepository
    ): GetFeedingPointsUseCase =
        GetFeedingPointsUseCase(feedingPointRepository)
}