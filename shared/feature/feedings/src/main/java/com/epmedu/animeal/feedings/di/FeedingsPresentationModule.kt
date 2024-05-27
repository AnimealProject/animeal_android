package com.epmedu.animeal.feedings.di

import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.feedings.domain.usecase.GetHasNewPendingFeedingUseCase
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
        getHasNewPendingFeedingUseCase: GetHasNewPendingFeedingUseCase
    ): FeedingsButtonHandler = FeedingsButtonHandlerImpl(
        buildConfigProvider,
        getCurrentUserGroupUseCase,
        getHasNewPendingFeedingUseCase
    )
}