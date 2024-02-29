package com.epmedu.animeal.feedings.di

import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.feedings.domain.usecase.GetIsNewFeedingPendingUseCase
import com.epmedu.animeal.feedings.presentation.viewmodel.handlers.FeedingsButtonHandler
import com.epmedu.animeal.feedings.presentation.viewmodel.handlers.FeedingsButtonHandlerImpl
import com.epmedu.animeal.networkuser.domain.usecase.GetCurrentUserGroupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FeedingsPresentationModule {

    @ViewModelScoped
    @Provides
    fun providesFeedingsButtonHandler(
        buildConfigProvider: BuildConfigProvider,
        getCurrentUserGroupUseCase: GetCurrentUserGroupUseCase,
        getIsNewFeedingPendingUseCase: GetIsNewFeedingPendingUseCase
    ): FeedingsButtonHandler = FeedingsButtonHandlerImpl(
        buildConfigProvider,
        getCurrentUserGroupUseCase,
        getIsNewFeedingPendingUseCase
    )
}