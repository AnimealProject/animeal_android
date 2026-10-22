package com.epmedu.animeal.push.di

import com.epmedu.animeal.push.domain.repository.PushRepository
import com.epmedu.animeal.push.domain.usecase.RegisterDeviceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PushDomainModule {

    @ViewModelScoped
    @Provides
    fun providesRegisterDeviceUseCase(
        repo: PushRepository,
    ) = RegisterDeviceUseCase(repo)
}