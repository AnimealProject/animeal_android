package com.epmedu.animeal.di

import com.epmedu.animeal.MainState
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object MainScopeModule {

    @ViewModelScoped
    @Provides
    fun providesStateDelegate(): StateDelegate<MainState> = DefaultStateDelegate(MainState())
}