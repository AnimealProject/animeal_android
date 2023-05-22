package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.domain.usecase.GetAllFeedingPointsUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingPointByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FeedingDomainModule {

    @ViewModelScoped
    @Provides
    fun providesGetAllFeedingPointsUseCase(
        feedingPointRepository: FeedingPointRepository
    ): GetAllFeedingPointsUseCase = GetAllFeedingPointsUseCase(feedingPointRepository)

    @ViewModelScoped
    @Provides
    fun providesGetFeedingPointByIdUseCase(
        feedingPointRepository: FeedingPointRepository
    ): GetFeedingPointByIdUseCase = GetFeedingPointByIdUseCase(feedingPointRepository)
}