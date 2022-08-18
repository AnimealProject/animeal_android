package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.extensions.DAY_MONTH_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.common.validation.ProfileValidator
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
) : StateViewModel<FinishProfileState, FinishProfileEvent>(initialState = FinishProfileState()) {

    private val validator: ProfileValidator = ProfileValidator()

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
                    copy(nameError = validator.validateName(state.name).errorMessage)
                }
            }
            ValidateSurname -> {
                updateState {
                    copy(surnameError = validator.validateSurname(state.surname).errorMessage)
                }
            }
            ValidateEmail -> {
                updateState {
                    copy(emailError = validator.validateEmail(state.email).errorMessage)
                }
            }
            ValidateBirthDate -> {
                updateState {
                    copy(birthDateError = validator.validateBirthDate(state.formattedBirthDate).errorMessage)
                }
            }
        }
    }

    private fun submitData() {
        updateState {
            copy(
                nameError = validator.validateName(state.name).errorMessage,
                surnameError = validator.validateSurname(state.surname).errorMessage,
                emailError = validator.validateEmail(state.email).errorMessage,
                birthDateError = validator.validateBirthDate(state.formattedBirthDate).errorMessage
            )
        }

        if (state.hasErrors()) {
            return
        }

        saveProfile()
    }

    private fun saveProfile() {
        val profile = Profile(
            firstName = state.name,
            lastName = state.surname,
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
                        name = it.firstName,
                        surname = it.lastName,
                        email = it.email,
                        formattedBirthDate = it.birthDate,
                        formattedPhoneNumber = it.phoneNumber
                    )
                }
            }
        }
    }
}