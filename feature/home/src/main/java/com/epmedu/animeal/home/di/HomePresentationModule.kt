@file:Suppress("LongParameterList", "TooManyFunctions")

package com.epmedu.animeal.home.di

import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.home.domain.usecases.CancelFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.FinishFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.GetAllFeedingPointsUseCase
import com.epmedu.animeal.home.domain.usecases.RejectFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.StartFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateAnimalTypeSettingsUseCase
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent
import com.epmedu.animeal.home.presentation.viewmodel.handlers.DefaultHomeHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.error.DefaultErrorHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.error.ErrorHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding.DefaultFeedingHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding.FeedingHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint.DefaultFeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.DefaultGpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.DefaultLocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.DefaultRouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timer.DefaultTimerHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timer.TimerHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation.DefaultTimerCancellationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation.TimerCancellationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.DefaultWillFeedHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.WillFeedHandler
import com.epmedu.animeal.timer.domain.usecase.DisableTimerUseCase
import com.epmedu.animeal.timer.domain.usecase.StartTimerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object HomePresentationModule {

    @ViewModelScoped
    @Provides
    fun providesStateDelegate(): StateDelegate<HomeState> = DefaultStateDelegate(HomeState())

    @ViewModelScoped
    @Provides
    fun providesEventDelegate(): EventDelegate<HomeViewModelEvent> = DefaultEventDelegate()

    @ViewModelScoped
    @Provides
    fun providesRouteHandler(
        stateDelegate: StateDelegate<HomeState>
    ): RouteHandler = DefaultRouteHandler(stateDelegate)

    @ViewModelScoped
    @Provides
    fun providesWillFeedHandler(
        stateDelegate: StateDelegate<HomeState>
    ): WillFeedHandler = DefaultWillFeedHandler(stateDelegate)

    @ViewModelScoped
    @Provides
    fun providesLocationHandler(
        stateDelegate: StateDelegate<HomeState>
    ): LocationHandler = DefaultLocationHandler(stateDelegate)

    @ViewModelScoped
    @Provides
    fun providesGpsHandler(
        stateDelegate: StateDelegate<HomeState>
    ): GpsHandler = DefaultGpsHandler(stateDelegate)

    @ViewModelScoped
    @Provides
    fun providesFeedingHandler(
        stateDelegate: StateDelegate<HomeState>,
        actionDelegate: ActionDelegate,
        routeHandler: RouteHandler,
        errorHandler: ErrorHandler,
        feedingPointHandler: FeedingPointHandler,
        timerHandler: TimerHandler,
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
        startFeedingUseCase,
        cancelFeedingUseCase,
        rejectFeedingUseCase,
        finishFeedingUseCase
    )

    @ViewModelScoped
    @Provides
    fun providesFeedingPointHandler(
        stateDelegate: StateDelegate<HomeState>,
        eventDelegate: EventDelegate<HomeViewModelEvent>,
        actionDelegate: ActionDelegate,
        errorHandler: ErrorHandler,
        getAllFeedingPointsUseCase: GetAllFeedingPointsUseCase,
        addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
        removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
        updateAnimalTypeSettingsUseCase: UpdateAnimalTypeSettingsUseCase,
    ): FeedingPointHandler = DefaultFeedingPointHandler(
        stateDelegate,
        eventDelegate,
        actionDelegate,
        errorHandler,
        getAllFeedingPointsUseCase,
        addFeedingPointToFavouritesUseCase,
        removeFeedingPointFromFavouritesUseCase,
        updateAnimalTypeSettingsUseCase
    )

    @ViewModelScoped
    @Provides
    fun providesTimerHandler(
        stateDelegate: StateDelegate<HomeState>,
        routeHandler: RouteHandler,
        feedingPointHandler: FeedingPointHandler,
        startTimerUseCase: StartTimerUseCase,
        disableTimerUseCase: DisableTimerUseCase
    ): TimerHandler = DefaultTimerHandler(
        stateDelegate,
        routeHandler,
        feedingPointHandler,
        startTimerUseCase,
        disableTimerUseCase
    )

    @ViewModelScoped
    @Provides
    fun providesTimerCancellationHandler(
        stateDelegate: StateDelegate<HomeState>,
        feedingPointHandler: FeedingPointHandler,
        timerHandler: TimerHandler,
        feedingHandler: FeedingHandler
    ): TimerCancellationHandler = DefaultTimerCancellationHandler(
        stateDelegate,
        feedingPointHandler,
        timerHandler,
        feedingHandler
    )

    @ViewModelScoped
    @Provides
    fun providesErrorHandler(
        stateDelegate: StateDelegate<HomeState>
    ): ErrorHandler = DefaultErrorHandler(stateDelegate)

    @ViewModelScoped
    @Provides
    fun providesHomeHandler(
        feedingPointHandler: FeedingPointHandler,
        routeHandler: RouteHandler,
        willFeedHandler: WillFeedHandler,
        feedingHandler: FeedingHandler,
        locationHandler: LocationHandler,
        timerHandler: TimerHandler,
        timerCancellationHandler: TimerCancellationHandler,
        gpsHandler: GpsHandler,
        errorHandler: ErrorHandler
    ) = DefaultHomeHandler(
        feedingPointHandler,
        routeHandler,
        willFeedHandler,
        feedingHandler,
        locationHandler,
        timerHandler,
        timerCancellationHandler,
        gpsHandler,
        errorHandler
    )
}