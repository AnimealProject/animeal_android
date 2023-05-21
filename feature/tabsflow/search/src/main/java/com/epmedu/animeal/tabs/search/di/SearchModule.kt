package com.epmedu.animeal.tabs.search.di

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.tabs.search.domain.SearchFeedingPointsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object SearchModule {

    @ViewModelScoped
    @Provides
    fun provideSearchFeedingPointsUseCase(
        feedingPointRepository: FeedingPointRepository
    ): SearchFeedingPointsUseCase = SearchFeedingPointsUseCase(feedingPointRepository)
}