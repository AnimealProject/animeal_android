package com.epmedu.animeal.signup.di

import com.epmedu.animeal.router.domain.RouterRepository
import com.epmedu.animeal.signup.domain.GetSignUpStartDestinationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SignUpModule {

    @ViewModelScoped
    @Provides
    fun provideGetSignUpStartDestinationUseCase(
        repository: RouterRepository
    ) = GetSignUpStartDestinationUseCase(repository)
}