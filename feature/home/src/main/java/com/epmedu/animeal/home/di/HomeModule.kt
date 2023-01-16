package com.epmedu.animeal.home.di

import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.data.repository.FeedingPointRepositoryImpl
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.home.data.ApplicationSettingsRepository
import com.epmedu.animeal.home.data.ApplicationSettingsRepositoryImpl
import com.epmedu.animeal.home.domain.usecases.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.usecases.SaveUserAsFeederUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding.DefaultFeedingHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding.FeedingHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.DefaultGpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.DefaultLocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.DefaultRouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.DefaultWillFeedHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.WillFeedHandler
import com.epmedu.animeal.profile.data.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Suppress("TooManyFunctions")
@Module
@InstallIn(ViewModelComponent::class)
internal object HomeModule {

    @ViewModelScoped
    @Provides
    fun providesFeedingPointRepository(): FeedingPointRepository = FeedingPointRepositoryImpl()

    @ViewModelScoped
    @Provides
    fun providesStateDelegate(): StateDelegate<HomeState> = DefaultStateDelegate(HomeState())

    @ViewModelScoped
    @Provides
    fun providesRouteHandler(
        stateDelegate: StateDelegate<HomeState>,
        feedingHandler: FeedingHandler
    ): RouteHandler = DefaultRouteHandler(stateDelegate, feedingHandler)

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
        feedingPointRepository: FeedingPointRepository,
        saveUserAsFeederUseCase: SaveUserAsFeederUseCase,
        stateDelegate: StateDelegate<HomeState>
    ): FeedingHandler = DefaultFeedingHandler(
        feedingPointRepository,
        saveUserAsFeederUseCase,
        stateDelegate
    )

    @ViewModelScoped
    @Provides
    fun providesApplicationSettingsRepository(
        appSettingsProvider: AppSettingsProvider,
    ): ApplicationSettingsRepository = ApplicationSettingsRepositoryImpl(appSettingsProvider)

    @ViewModelScoped
    @Provides
    fun providesGetGeolocationPermissionRequestedSettingUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository,
    ): GetGeolocationPermissionRequestedSettingUseCase =
        GetGeolocationPermissionRequestedSettingUseCase(applicationSettingsRepository)

    @ViewModelScoped
    @Provides
    fun providesUpdateGeolocationPermissionRequestedSettingUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository,
    ): UpdateGeolocationPermissionRequestedSettingUseCase =
        UpdateGeolocationPermissionRequestedSettingUseCase(applicationSettingsRepository)

    @ViewModelScoped
    @Provides
    fun providesSaveUserAsFeederUseCase(
        feedingPointRepository: FeedingPointRepository,
        profileRepository: ProfileRepository
    ): SaveUserAsFeederUseCase =
        SaveUserAsFeederUseCase(feedingPointRepository, profileRepository)
}