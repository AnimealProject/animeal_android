package com.epmedu.animeal.networkuser.di

import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.networkuser.domain.usecase.DeleteNetworkUserUseCase
import com.epmedu.animeal.networkuser.domain.usecase.GetCurrentUserGroupUseCase
import com.epmedu.animeal.networkuser.domain.usecase.GetIsPhoneNumberVerifiedUseCase
import com.epmedu.animeal.networkuser.domain.usecase.GetIsTrustedUseCase
import com.epmedu.animeal.networkuser.domain.usecase.GetNetworkProfileUseCase
import com.epmedu.animeal.networkuser.domain.usecase.LogOutUseCase
import com.epmedu.animeal.networkuser.domain.usecase.UpdateNetworkProfileUseCase
import com.epmedu.animeal.router.domain.RouterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object NetworkDomainModule {

    @ViewModelScoped
    @Provides
    fun provideGetCurrentUserGroupUseCase(
        repository: NetworkRepository
    ) = GetCurrentUserGroupUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetIsPhoneNumberVerifiedUseCase(
        repository: NetworkRepository,
    ) = GetIsPhoneNumberVerifiedUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetIsTrustedUseCase(
        repository: NetworkRepository
    ) = GetIsTrustedUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetNetworkProfileUseCase(
        repository: NetworkRepository,
        getCurrentUserGroupUseCase: GetCurrentUserGroupUseCase
    ) = GetNetworkProfileUseCase(repository, getCurrentUserGroupUseCase)

    @ViewModelScoped
    @Provides
    fun provideUpdateNetworkProfileUseCase(
        repository: NetworkRepository,
    ) = UpdateNetworkProfileUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideDeleteNetworkUserUseCase(
        repository: NetworkRepository,
    ) = DeleteNetworkUserUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideLogOutUseCase(
        networkRepository: NetworkRepository,
        routerRepository: RouterRepository
    ) = LogOutUseCase(networkRepository, routerRepository)
}