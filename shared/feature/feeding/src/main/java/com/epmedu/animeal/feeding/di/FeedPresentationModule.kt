package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.usecase.MotivateUseGpsSettingUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateMotivateUseGpsSettingsUseCase
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.DefaultWillFeedHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.WillFeedHandler
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object FeedPresentationModule {
    @ViewModelScoped
    @Provides
    fun providesWillFeedStateDelegate(): StateDelegate<WillFeedState> =
        DefaultStateDelegate(WillFeedState())

    @ViewModelScoped
    @Provides
    fun providesWillFeedHandler(
        stateDelegate: StateDelegate<WillFeedState>,
        motivateUseGpsLocationSettingUseCase: MotivateUseGpsSettingUseCase,
        updateMotivateUseGpsLocationSettingsUseCase: UpdateMotivateUseGpsSettingsUseCase,
        gpsSettingsProvider: GpsSettingsProvider
    ): WillFeedHandler = DefaultWillFeedHandler(
        stateDelegate,
        motivateUseGpsLocationSettingUseCase,
        updateMotivateUseGpsLocationSettingsUseCase,
        gpsSettingsProvider
    )
}