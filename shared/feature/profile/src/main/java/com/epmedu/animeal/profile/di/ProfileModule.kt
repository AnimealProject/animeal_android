package com.epmedu.animeal.profile.di

import com.epmedu.animeal.common.di.gson.AbsGsonTypeAdapter
import com.epmedu.animeal.foundation.common.validation.validator.DefaultProfileValidator
import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator
import com.epmedu.animeal.profile.data.repository.ProfileRepositoryImpl
import com.epmedu.animeal.profile.domain.ClearProfileUseCase
import com.epmedu.animeal.profile.domain.GetProfileUseCase
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.profile.domain.ValidateEmailUseCase
import com.epmedu.animeal.profile.domain.ValidateNameUseCase
import com.epmedu.animeal.profile.domain.ValidatePhoneNumberUseCase
import com.epmedu.animeal.profile.domain.ValidateSurnameUseCase
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import com.epmedu.animeal.profile.presentation.viewmodel.handler.ProfileInputFormHandler
import com.epmedu.animeal.profile.presentation.viewmodel.handler.ProfileInputFormHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ViewModelComponent::class)
internal object ProfileModule {

    @ViewModelScoped
    @Provides
    fun provideProfileValidator(): ProfileValidator = DefaultProfileValidator()

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
    fun provideClearProfileUseCase(
        repository: ProfileRepository
    ) = ClearProfileUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideProfileInputFormHandler(
        validateNameUseCase: ValidateNameUseCase,
        validateSurnameUseCase: ValidateSurnameUseCase,
        validateEmailUseCase: ValidateEmailUseCase,
        validatePhoneNumberUseCase: ValidatePhoneNumberUseCase
    ): ProfileInputFormHandler = ProfileInputFormHandlerImpl(
        validateNameUseCase,
        validateSurnameUseCase,
        validateEmailUseCase,
        validatePhoneNumberUseCase
    )
}

@Module
@InstallIn(SingletonComponent::class)
internal interface ProfileBinding {

    @Binds
    fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @IntoSet
    @Binds
    fun bindBasicUserAdapter(impl: BasicProfileTypeAdapter): AbsGsonTypeAdapter<*>
}
