package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.extensions.DAY_MONTH_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.common.validation.ProfileValidator
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent
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
            is FinishProfileScreenEvent.NameChanged -> {
                updateState { copy(name = event.name) }
            }
            is FinishProfileScreenEvent.SurnameChanged -> {
                updateState { copy(surname = event.surname) }
            }
            is FinishProfileScreenEvent.EmailChanged -> {
                updateState { copy(email = event.email) }
            }
            is FinishProfileScreenEvent.BirthDateChanged -> {
                updateState {
                    copy(
                        formattedBirthDate = formatDateToString(
                            date = event.birthDate,
                            formatter = DAY_MONTH_COMMA_YEAR_FORMATTER
                        )
                    )
                }
            }
            FinishProfileScreenEvent.Submit -> {
                submitData()
            }
            FinishProfileScreenEvent.ValidateName -> {
                updateState {
                    copy(nameError = validator.validateName(state.name).errorMessage)
                }
            }
            FinishProfileScreenEvent.ValidateSurname -> {
                updateState {
                    copy(surnameError = validator.validateSurname(state.surname).errorMessage)
                }
            }
            FinishProfileScreenEvent.ValidateEmail -> {
                updateState {
                    copy(emailError = validator.validateEmail(state.email).errorMessage)
                }
            }
            FinishProfileScreenEvent.ValidateBirthDate -> {
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
                sendEvent(FinishProfileEvent.Saved)
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