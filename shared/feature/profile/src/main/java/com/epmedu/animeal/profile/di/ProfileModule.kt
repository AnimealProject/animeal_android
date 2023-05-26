package com.epmedu.animeal.profile.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.foundation.common.validation.validator.DefaultProfileValidator
import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator
import com.epmedu.animeal.profile.data.repository.ProfileRepositoryImpl
import com.epmedu.animeal.profile.domain.ClearProfileUseCase
import com.epmedu.animeal.profile.domain.GetProfileUseCase
import com.epmedu.animeal.profile.domain.LogOutUseCase
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.profile.domain.ValidateBirthDateUseCase
import com.epmedu.animeal.profile.domain.ValidateEmailUseCase
import com.epmedu.animeal.profile.domain.ValidateNameUseCase
import com.epmedu.animeal.profile.domain.ValidatePhoneNumberUseCase
import com.epmedu.animeal.profile.domain.ValidateSurnameUseCase
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import com.epmedu.animeal.router.domain.RouterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object ProfileModule {

    @ViewModelScoped
    @Provides
    fun provideProfileValidator(): ProfileValidator = DefaultProfileValidator()

    @ViewModelScoped
    @Provides
    fun provideProfileRepository(
        dataStore: DataStore<Preferences>,
        authAPI: AuthAPI,
    ): ProfileRepository =
        ProfileRepositoryImpl(dataStore, authAPI)

    @ViewModelScoped
    @Provides
    fun provideGetProfileUseCase(
        repository: ProfileRepository
    ) = GetProfileUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSaveProfileUseCase(
        repository: ProfileRepository
    ) = SaveProfileUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideValidateNameUseCase(
        validator: ProfileValidator
    ) = ValidateNameUseCase(validator)

    @ViewModelScoped
    @Provides
    fun provideValidateSurnameUseCase(
        validator: ProfileValidator
    ) = ValidateSurnameUseCase(validator)

    @ViewModelScoped
    @Provides
    fun provideValidateEmailUseCase(
        validator: ProfileValidator
    ) = ValidateEmailUseCase(validator)

    @ViewModelScoped
    @Provides
    fun provideValidatePhoneNumberUseCase(
        validator: ProfileValidator
    ) = ValidatePhoneNumberUseCase(validator)

    @ViewModelScoped
    @Provides
    fun provideValidateBirthDateUseCase(
        validator: ProfileValidator
    ) = ValidateBirthDateUseCase(validator)

    @ViewModelScoped
    @Provides
    fun provideLogOutUseCase(
        profileRepository: ProfileRepository,
        routerRepository: RouterRepository
    ) = LogOutUseCase(profileRepository, routerRepository)

    @ViewModelScoped
    @Provides
    fun provideClearProfileUseCase(
        repository: ProfileRepository
    ) = ClearProfileUseCase(repository)
}