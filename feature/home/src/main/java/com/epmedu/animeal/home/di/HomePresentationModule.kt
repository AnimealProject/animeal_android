package com.epmedu.animeal.home.di

import com.epmedu.animeal.camera.domain.usecase.DeletePhotoUseCase
import com.epmedu.animeal.camera.domain.usecase.UploadPhotoUseCase
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.domain.usecase.ForcedArgumentsUseCase
import com.epmedu.animeal.common.presentation.viewmodel.HomeViewModelEvent
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.DefaultErrorHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.DefaultHomeHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.camera.CameraHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.camera.DefaultCameraHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gallery.DefaultFeedingPhotoGalleryHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gallery.FeedingPhotoGalleryHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.DefaultGpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.DefaultLocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation.DefaultTimerCancellationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation.TimerCancellationHandler
import com.epmedu.animeal.router.presentation.DefaultRouteHandler
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.domain.usecase.DisableTimerUseCase
import com.epmedu.animeal.timer.domain.usecase.StartTimerUseCase
import com.epmedu.animeal.timer.presentation.handler.DefaultTimerHandler
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
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
    fun providesStateDelegate(
        buildConfigProvider: BuildConfigProvider
    ): StateDelegate<HomeState> = DefaultStateDelegate(
        initialState = HomeState(
            mapBoxPublicKey = buildConfigProvider.mapBoxPublicKey,
            mapBoxStyleUri = buildConfigProvider.mapBoxStyleURI
        )
    )

    @ViewModelScoped
    @Provides
    fun providesFeedingPointStateDelegate(): StateDelegate<FeedingPointState> =
        DefaultStateDelegate(FeedingPointState())

    @ViewModelScoped
    @Provides
    fun providesFeedingRouteStateDelegate(): StateDelegate<FeedingRouteState> =
        DefaultStateDelegate(FeedingRouteState.Disabled)

    @ViewModelScoped
    @Provides
    fun providesEventDelegate(): EventDelegate<HomeViewModelEvent> = DefaultEventDelegate()

    @ViewModelScoped
    @Provides
    fun providesRouteHandler(
        stateDelegate: StateDelegate<FeedingRouteState>,
        forcedFeedingPoint: ForcedArgumentsUseCase
    ): RouteHandler = DefaultRouteHandler(
        stateDelegate,
        forcedFeedingPoint
    )

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
    fun providesCameraHandler(
        stateDelegate: StateDelegate<HomeState>,
        actionDelegate: ActionDelegate,
        uploadPhotoUseCase: UploadPhotoUseCase
    ): CameraHandler = DefaultCameraHandler(
        stateDelegate,
        actionDelegate,
        uploadPhotoUseCase
    )

    @ViewModelScoped
    @Provides
    fun providesTimerHandler(
        routeHandler: RouteHandler,
        startTimerUseCase: StartTimerUseCase,
        disableTimerUseCase: DisableTimerUseCase
    ): TimerHandler = DefaultTimerHandler(
        routeHandler,
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
        cameraHandler: DefaultCameraHandler,
        feedingPointHandler: FeedingPointHandler,
        routeHandler: RouteHandler,
        feedingHandler: FeedingHandler,
        locationHandler: LocationHandler,
        timerHandler: TimerHandler,
        timerCancellationHandler: TimerCancellationHandler,
        gpsHandler: GpsHandler,
        errorHandler: ErrorHandler
    ) = DefaultHomeHandler(
        cameraHandler,
        feedingPointHandler,
        routeHandler,
        feedingHandler,
        locationHandler,
        timerHandler,
        timerCancellationHandler,
        gpsHandler,
        errorHandler
    )

    @ViewModelScoped
    @Provides
    fun providePhotoGalleryHandler(
        deletePhotoUseCase: DeletePhotoUseCase,
        stateDelegate: StateDelegate<HomeState>,
        actionDelegate: ActionDelegate
    ): FeedingPhotoGalleryHandler =
        DefaultFeedingPhotoGalleryHandler(
            deletePhotoUseCase,
            stateDelegate,
            actionDelegate
        )
}