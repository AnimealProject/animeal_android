package com.epmedu.animeal.tabs.di

import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.presentation.viewmodel.TimerState
import com.epmedu.animeal.tabs.viewmodel.handlers.timer.DefaultTimerHandler
import com.epmedu.animeal.tabs.viewmodel.handlers.timer.TimerHandler
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

    @ViewModelScoped
    @Provides
    fun providesTimerHandler(
        stateDelegate: StateDelegate<TimerState>
    ): TimerHandler = DefaultTimerHandler(stateDelegate)
}