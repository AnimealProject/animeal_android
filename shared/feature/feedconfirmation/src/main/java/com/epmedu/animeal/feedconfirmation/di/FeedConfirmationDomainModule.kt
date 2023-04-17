package com.epmedu.animeal.feedconfirmation.di

import com.epmedu.animeal.feedconfirmation.domain.ValidateFeedingPointAvailableUseCase
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FeedConfirmationDomainModule {

    @ViewModelScoped
    @Provides
    fun providesValidateFeedingPointAvailableUseCase(
        feedingPointRepository: FeedingPointRepository
    ): ValidateFeedingPointAvailableUseCase =
        ValidateFeedingPointAvailableUseCase(feedingPointRepository)

}