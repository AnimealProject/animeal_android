package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.DAY_MONTH_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult
import com.epmedu.animeal.foundation.common.validation.validator.DefaultProfileValidator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.BirthDateChanged
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.EmailChanged
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.NameChanged
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.Submit
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.SurnameChanged
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ValidateBirthDate
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ValidateEmail
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ValidateName
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ValidateSurname
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileEvent.Saved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FinishProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(),
    StateDelegate<FinishProfileState> by DefaultStateDelegate(initialState = FinishProfileState()),
    EventDelegate<FinishProfileEvent> by DefaultEventDelegate() {

    private val validator: DefaultProfileValidator = DefaultProfileValidator()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            profileRepository.getProfile().collect {
                updateState {
                    copy(
                        name = it.name,
                        surname = it.surname,
                        email = it.email,
                        formattedBirthDate = it.birthDate,
                        formattedPhoneNumber = it.phoneNumber
                    )
                }
            }
        }
    }

    fun handleEvents(event: FinishProfileScreenEvent) {
        when (event) {
            is NameChanged -> {
                updateState { copy(name = event.name) }
            }
            is SurnameChanged -> {
                updateState { copy(surname = event.surname) }
            }
            is EmailChanged -> {
                updateState { copy(email = event.email) }
            }
            is BirthDateChanged -> {
                updateState {
                    copy(
                        formattedBirthDate = formatDateToString(
                            date = event.birthDate,
                            formatter = DAY_MONTH_COMMA_YEAR_FORMATTER
                        )
                    )
                }
            }
            Submit -> {
                submitData()
            }
            ValidateName -> {
                updateState { copy(nameError = validateName(name)) }
            }
            ValidateSurname -> {
                updateState { copy(surnameError = validateSurname(surname)) }
            }
            ValidateEmail -> {
                updateState { copy(emailError = validateEmail(email)) }
            }
            ValidateBirthDate -> {
                updateState {
                    copy(birthDateError = validateBirthdate(formattedBirthDate))
                }
            }
        }
    }

    private fun submitData() {
        updateState {
            copy(
                nameError = validateName(name),
                surnameError = validateSurname(surname),
                emailError = validateEmail(email),
                birthDateError = validateBirthdate(formattedBirthDate),
            )
        }

        if (state.hasErrors()) {
            return
        }

        saveProfile()
    }

    private fun saveProfile() {
        val profile = Profile(
            name = state.name,
            surname = state.surname,
            email = state.email,
            birthDate = state.formattedBirthDate
        )
        viewModelScope.launch {
            profileRepository.saveProfile(profile).collect {
                sendEvent(Saved)
            }
        }
    }

    private fun validateName(name: String): UiText {
        return when (val result = validator.validateName(name)) {
            is NameValidationResult.ValidName -> {
                UiText.Empty
            }
            is NameValidationResult.BlankNameError -> {
                UiText.StringResource(R.string.profile_name_blank_error_msg)
            }
            is NameValidationResult.WrongNameLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
        }
    }

    private fun validateSurname(surname: String): UiText {
        return when (val result = validator.validateSurname(surname)) {
            is SurnameValidationResult.ValidSurname -> {
                UiText.Empty
            }
            is SurnameValidationResult.BlankSurnameError -> {
                UiText.StringResource(R.string.profile_surname_blank_error_msg)
            }
            is SurnameValidationResult.WrongSurnameLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
        }
    }

    private fun validateEmail(email: String): UiText {
        return when (val result = validator.validateEmail(email)) {
            is EmailValidationResult.ValidEmail -> {
                UiText.Empty
            }
            is EmailValidationResult.BlankEmailError -> {
                UiText.StringResource(R.string.profile_email_blank_error_msg)
            }
            is EmailValidationResult.WrongEmailLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
            is EmailValidationResult.InvalidEmailError -> {
                UiText.StringResource(R.string.profile_email_invalid_error_msg)
            }
        }
    }

    private fun validateBirthdate(birthDate: String): UiText {
        return when (validator.validateBirthDate(birthDate)) {
            is BirthDateValidationResult.ValidBirthDate -> {
                UiText.Empty
            }
            is BirthDateValidationResult.BlankBirthDateError -> {
                UiText.StringResource(R.string.profile_select_birth_date)
            }
        }
    }
}