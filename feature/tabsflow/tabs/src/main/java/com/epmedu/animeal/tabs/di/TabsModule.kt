package com.epmedu.animeal.tabs.di

import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.timer.data.model.TimerState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object TabsModule {

    @ViewModelScoped
    @Provides
    fun providesStateDelegate(): StateDelegate<TimerState> = DefaultStateDelegate(TimerState.Disabled)
}