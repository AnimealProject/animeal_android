package com.epmedu.animeal.feeding.di

import androidx.lifecycle.SavedStateHandle
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.CancelFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.ExpireFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.FinishFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetAllFeedingPointsUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetApprovedFeedingHistoriesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedStateUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingInProgressUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingPointByIdUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingPointByPriorityUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.StartFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateAnimalTypeSettingsUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateFeedStateUseCase
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.DefaultFeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.DefaultFeedingPointHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.networkuser.domain.usecase.GetIsTrustedUseCase
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object FeedingPresentationModule {

    @ViewModelScoped
    @Provides
    fun providesFeedStateDelegate(): StateDelegate<FeedState> = DefaultStateDelegate(FeedState())

    @ViewModelScoped
    @Provides
    fun providesFeedingHandler(
        stateDelegate: StateDelegate<FeedState>,
        actionDelegate: ActionDelegate,
        routeHandler: RouteHandler,
        errorHandler: ErrorHandler,
        feedingPointHandler: FeedingPointHandler,
        timerHandler: TimerHandler,
        fetchCurrentFeedingPointUseCase: FetchCurrentFeedingPointUseCase,
        getFeedingPointByIdUseCase: GetFeedingPointByIdUseCase,
        getFeedStateUseCase: GetFeedStateUseCase,
        updateFeedStateUseCase: UpdateFeedStateUseCase,
        startFeedingUseCase: StartFeedingUseCase,
        cancelFeedingUseCase: CancelFeedingUseCase,
        expireFeedingUseCase: ExpireFeedingUseCase,
        finishFeedingUseCase: FinishFeedingUseCase,
        getIsTrustedUseCase: GetIsTrustedUseCase
    ): FeedingHandler = DefaultFeedingHandler(
        stateDelegate,
        actionDelegate,
        routeHandler,
        errorHandler,
        feedingPointHandler,
        timerHandler,
        fetchCurrentFeedingPointUseCase,
        getFeedingPointByIdUseCase,
        getFeedStateUseCase,
        updateFeedStateUseCase,
        startFeedingUseCase,
        cancelFeedingUseCase,
        expireFeedingUseCase,
        finishFeedingUseCase,
        getIsTrustedUseCase
    )

    @ViewModelScoped
    @Provides
    fun providesFeedingPointHandler(
        stateDelegate: StateDelegate<FeedingPointState>,
        actionDelegate: ActionDelegate,
        routeHandler: RouteHandler,
        errorHandler: ErrorHandler,
        savedStateHandle: SavedStateHandle,
        getFeedStateUseCase: GetFeedStateUseCase,
        getFeedingPointByPriorityUseCase: GetFeedingPointByPriorityUseCase,
        getAllFeedingPointsUseCase: GetAllFeedingPointsUseCase,
        getFeedingPointByIdUseCase: GetFeedingPointByIdUseCase,
        getFeedingHistoriesUseCase: GetApprovedFeedingHistoriesUseCase,
        getFeedingInProgressUseCase: GetFeedingInProgressUseCase,
        addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
        removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
        updateAnimalTypeSettingsUseCase: UpdateAnimalTypeSettingsUseCase,
    ): FeedingPointHandler = DefaultFeedingPointHandler(
        stateDelegate,
        actionDelegate,
        routeHandler,
        errorHandler,
        savedStateHandle,
        getFeedStateUseCase,
        getFeedingPointByPriorityUseCase,
        getAllFeedingPointsUseCase,
        getFeedingPointByIdUseCase,
        getFeedingHistoriesUseCase,
        getFeedingInProgressUseCase,
        addFeedingPointToFavouritesUseCase,
        removeFeedingPointFromFavouritesUseCase,
        updateAnimalTypeSettingsUseCase
    )
}