package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.profile.domain.GetProfileUseCase
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.profile.domain.ValidateBirthDateUseCase
import com.epmedu.animeal.profile.domain.ValidateEmailUseCase
import com.epmedu.animeal.profile.domain.ValidateNameUseCase
import com.epmedu.animeal.profile.domain.ValidatePhoneNumberUseCase
import com.epmedu.animeal.profile.domain.ValidateSurnameUseCase
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.PhoneNumberChanged
import com.epmedu.animeal.profile.presentation.mapper.BirthDateValidationResultToUiTextMapper
import com.epmedu.animeal.profile.presentation.mapper.EmailValidationResultToUiTextMapper
import com.epmedu.animeal.profile.presentation.mapper.NameValidationResultToUiTextMapper
import com.epmedu.animeal.profile.presentation.mapper.PhoneNumberValidationResultToUiTextMapper
import com.epmedu.animeal.profile.presentation.mapper.SurnameValidationResultToUiTextMapper
import com.epmedu.animeal.profile.presentation.viewmodel.BaseProfileViewModel
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState.FormState.EDITABLE
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.Submit
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileEvent.Saved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("LongParameterList")
@HiltViewModel
internal class FinishProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    validateNameUseCase: ValidateNameUseCase,
    validateSurnameUseCase: ValidateSurnameUseCase,
    validateEmailUseCase: ValidateEmailUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    validateBirthDateUseCase: ValidateBirthDateUseCase,
    nameValidationResultToUiTextMapper: NameValidationResultToUiTextMapper,
    surnameValidationResultToUiTextMapper: SurnameValidationResultToUiTextMapper,
    emailValidationResultToUiTextMapper: EmailValidationResultToUiTextMapper,
    private val phoneNumberValidationResultToUiTextMapper: PhoneNumberValidationResultToUiTextMapper,
    birthDateValidationResultToUiTextMapper: BirthDateValidationResultToUiTextMapper
) : BaseProfileViewModel(
    validateNameUseCase,
    validateSurnameUseCase,
    validateEmailUseCase,
    validateBirthDateUseCase,
    nameValidationResultToUiTextMapper,
    surnameValidationResultToUiTextMapper,
    emailValidationResultToUiTextMapper,
    birthDateValidationResultToUiTextMapper
),
    EventDelegate<FinishProfileEvent> by DefaultEventDelegate() {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            getProfileUseCase.execute().collect {
                updateState {
                    copy(profile = it, formState = EDITABLE)
                }
            }
        }
    }

    override fun handlePhoneNumberChangedEvent(event: PhoneNumberChanged) {
        updateState {
            copy(
                profile = profile.copy(phoneNumber = event.phoneNumber),
                phoneNumberError = validateAndMapPhoneNumberToError(event.phoneNumber)
            )
        }
    }

    private fun validateAndMapPhoneNumberToError(phoneNumber: String): UiText {
        val validationResult = validatePhoneNumberUseCase.execute(phoneNumber)
        return phoneNumberValidationResultToUiTextMapper.map(validationResult)
    }

    fun handleScreenEvents(event: FinishProfileScreenEvent) {
        when (event) {
            Submit -> {
                submitProfile()
            }
        }
    }

    private fun submitProfile() {
        validateProfile()

        if (state.hasErrors().not()) saveProfile()
    }

    private fun validateProfile() {
        updateState {
            with(profile) {
                copy(
                    nameError = validateAndMapNameToError(name),
                    surnameError = validateAndMapSurnameToError(surname),
                    emailError = validateAndMapEmailToError(email),
                    phoneNumberError = validateAndMapPhoneNumberToError(phoneNumber),
                    birthDateError = validateAndMapBirthDateToError(birthDate)
                )
            }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            saveProfileUseCase.execute(state.profile).collect {
                sendEvent(Saved)
            }
        }
    }
}