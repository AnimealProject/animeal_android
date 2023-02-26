package com.epmedu.animeal.favourites.di

import com.epmedu.animeal.favourites.domain.GetFavouriteFeedingPointsUseCase
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object FavouritesModule {

    @ViewModelScoped
    @Provides
    fun provideGetFavouriteFeedingPointsUseCase(
        feedingPointRepository: FeedingPointRepository
    ): GetFavouriteFeedingPointsUseCase =
        GetFavouriteFeedingPointsUseCase(feedingPointRepository)
}