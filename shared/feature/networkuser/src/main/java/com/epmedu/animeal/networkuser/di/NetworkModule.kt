package com.epmedu.animeal.networkuser.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.UserAttributesAPI
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToProfileMapper
import com.epmedu.animeal.networkuser.data.mapper.ProfileToAuthUserAttributesMapper
import com.epmedu.animeal.networkuser.data.repository.NetworkRepositoryImpl
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.networkuser.domain.usecase.DeleteNetworkUserUseCase
import com.epmedu.animeal.networkuser.domain.usecase.GetIsPhoneNumberVerifiedUseCase
import com.epmedu.animeal.networkuser.domain.usecase.GetNetworkProfileUseCase
import com.epmedu.animeal.networkuser.domain.usecase.UpdateNetworkProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @ViewModelScoped
    @Provides
    fun provideNetworkRepository(
        authAPI: AuthAPI,
        userAttributesAPI: UserAttributesAPI,
        authUserAttributesToProfileMapper: AuthUserAttributesToProfileMapper,
        profileToAuthUserMapper: ProfileToAuthUserAttributesMapper,
    ): NetworkRepository =
        NetworkRepositoryImpl(
            authAPI,
            userAttributesAPI,
            authUserAttributesToProfileMapper,
            profileToAuthUserMapper,
        )

    @ViewModelScoped
    @Provides
    fun provideGetIsPhoneNumberVerifiedUseCase(
        repository: NetworkRepository,
    ) = GetIsPhoneNumberVerifiedUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetNetworkProfileUseCase(
        repository: NetworkRepository,
    ) = GetNetworkProfileUseCase(repository)

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
}