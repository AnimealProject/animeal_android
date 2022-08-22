package com.epmedu.animeal.more.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.DAY_MONTH_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.common.validation.ProfileValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(),
    StateDelegate<ProfileState> by DefaultStateDelegate(initialState = ProfileState()) {

    private val validator: ProfileValidator = ProfileValidator()

    init {
        loadProfile()

        stateFlow
            .onEach {
                updateState {
                    copy(enableButton = storedProfile != getCurrentProfile() || readonly)
                }
            }
            .launchIn(viewModelScope)
    }

    fun handleEvents(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.NameChanged -> {
                updateState { copy(name = event.name) }
            }
            is ProfileEvent.SurnameChanged -> {
                updateState { copy(surname = event.surname) }
            }
            is ProfileEvent.EmailChanged -> {
                updateState { copy(email = event.email) }
            }
            is ProfileEvent.BirthDateChanged -> {
                updateState {
                    copy(
                        formattedBirthDate = formatDateToString(
                            date = event.birthDate,
                            formatter = DAY_MONTH_COMMA_YEAR_FORMATTER
                        )
                    )
                }
            }
            ProfileEvent.Edit -> {
                updateState {
                    copy(readonly = false)
                }
            }
            ProfileEvent.Discard -> {
                discardChanges()
            }
            ProfileEvent.Save -> {
                saveChanges()
            }
            ProfileEvent.ValidateName -> {
                updateState {
                    copy(nameError = validator.validateName(state.name).errorMessage)
                }
            }
            ProfileEvent.ValidateSurname -> {
                updateState {
                    copy(surnameError = validator.validateSurname(state.surname).errorMessage)
                }
            }
            ProfileEvent.ValidateEmail -> {
                updateState {
                    copy(emailError = validator.validateEmail(state.email).errorMessage)
                }
            }
            ProfileEvent.ValidateBirthDate -> {
                updateState {
                    copy(birthDateError = validator.validateBirthDate(state.formattedBirthDate).errorMessage)
                }
            }
        }
    }

    private fun discardChanges() {
        updateState {
            copy(readonly = true)
        }
        loadProfile()
    }

    private fun saveChanges() {
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
        } else {
            updateState {
                copy(readonly = true)
            }
        }

        val profile = getCurrentProfile()

        viewModelScope.launch {
            profileRepository.saveProfile(profile).collect {
                updateState {
                    copy(storedProfile = profile)
                }
            }
        }
    }

    private fun getCurrentProfile() = Profile(
        firstName = state.name,
        lastName = state.surname,
        phoneNumber = state.formattedPhoneNumber,
        email = state.email,
        birthDate = state.formattedBirthDate
    )

    private fun loadProfile() {
        viewModelScope.launch {
            profileRepository.getProfile().collect {
                updateState {
                    copy(
                        storedProfile = it,
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