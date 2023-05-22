package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.common.presentation.viewmodel.HomeViewModelEvent
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.CancelFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.FetchFeedingPointByIdUseCase
import com.epmedu.animeal.feeding.domain.usecase.FinishFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetAllFeedingPointsUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedStateUseCase
import com.epmedu.animeal.feeding.domain.usecase.RejectFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.StartFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateAnimalTypeSettingsUseCase
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.DefaultFeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.DefaultFeedingPointHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
@Suppress("LongParameterList", "DEPRECATION")
internal object FeedPresentationModule {

    @ViewModelScoped
    @Provides
    fun providesFetchFeedingPointByIdUseCase(
        feedingPointRepository: FeedingPointRepository
    ): FetchFeedingPointByIdUseCase = FetchFeedingPointByIdUseCase(feedingPointRepository)

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
        fetchFeedingPointByIdUseCase: FetchFeedingPointByIdUseCase,
        getFeedStateUseCase: GetFeedStateUseCase,
        startFeedingUseCase: StartFeedingUseCase,
        cancelFeedingUseCase: CancelFeedingUseCase,
        rejectFeedingUseCase: RejectFeedingUseCase,
        finishFeedingUseCase: FinishFeedingUseCase,
    ): FeedingHandler = DefaultFeedingHandler(
        stateDelegate,
        actionDelegate,
        routeHandler,
        errorHandler,
        feedingPointHandler,
        timerHandler,
        fetchCurrentFeedingPointUseCase,
        fetchFeedingPointByIdUseCase,
        getFeedStateUseCase,
        startFeedingUseCase,
        cancelFeedingUseCase,
        rejectFeedingUseCase,
        finishFeedingUseCase,
    )

    @ViewModelScoped
    @Provides
    fun providesFeedingPointHandler(
        stateDelegate: StateDelegate<FeedingPointState>,
        eventDelegate: EventDelegate<HomeViewModelEvent>,
        actionDelegate: ActionDelegate,
        routeHandler: RouteHandler,
        errorHandler: ErrorHandler,
        getAllFeedingPointsUseCase: GetAllFeedingPointsUseCase,
        addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
        removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
        updateAnimalTypeSettingsUseCase: UpdateAnimalTypeSettingsUseCase,
    ): FeedingPointHandler = DefaultFeedingPointHandler(
        stateDelegate,
        eventDelegate,
        actionDelegate,
        routeHandler,
        errorHandler,
        getAllFeedingPointsUseCase,
        addFeedingPointToFavouritesUseCase,
        removeFeedingPointFromFavouritesUseCase,
        updateAnimalTypeSettingsUseCase
    )
}