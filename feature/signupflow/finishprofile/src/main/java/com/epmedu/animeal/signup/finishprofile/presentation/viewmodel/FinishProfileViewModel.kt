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
import com.epmedu.animeal.foundation.common.validation.DefaultProfileValidator
import com.epmedu.animeal.foundation.common.validation.ValidationResult
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
                updateState {
                    copy(nameError = reduceValidationResult(validator.validateName(state.name)))
                }
            }
            ValidateSurname -> {
                updateState {
                    copy(surnameError = reduceValidationResult(validator.validateSurname(state.surname)))
                }
            }
            ValidateEmail -> {
                updateState {
                    copy(emailError = reduceValidationResult(validator.validateEmail(state.email)))
                }
            }
            ValidateBirthDate -> {
                updateState {
                    copy(birthDateError = reduceValidationResult(validator.validateBirthDate(state.formattedBirthDate)))
                }
            }
        }
    }

    private fun submitData() {
        updateState {
            copy(
                nameError = reduceValidationResult(validator.validateName(state.name)),
                surnameError = reduceValidationResult(validator.validateSurname(state.surname)),
                emailError = reduceValidationResult(validator.validateEmail(state.email)),
                birthDateError = reduceValidationResult(validator.validateBirthDate(state.formattedBirthDate))
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

    private fun reduceValidationResult(result: ValidationResult) = when (result) {
        is ValidationResult.Success -> null
        is ValidationResult.Failure -> result.errorMessage
    }
}