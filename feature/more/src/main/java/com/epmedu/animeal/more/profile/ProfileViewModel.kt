package com.epmedu.animeal.more.profile

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.foundation.common.validation.ProfileValidator
import com.epmedu.animeal.foundation.input.formatBirthDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : StateViewModel<ProfileState>(initialState = ProfileState()) {

    private val validator: ProfileValidator = ProfileValidator()

    init {
        loadProfile()
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
                        birthDate = event.birthDate,
                        formattedBirthDate = formatBirthDate(event.birthDate)
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

        val profile = Profile(
            firstName = state.name,
            lastName = state.surname,
            email = state.email,
            birthDate = state.birthDate
        )

        viewModelScope.launch {
            profileRepository.saveProfile(profile).collect()
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
                        birthDate = it.birthDate,
                        formattedBirthDate = formatBirthDate(it.birthDate),
                        formattedPhoneNumber = it.phoneNumber
                    )
                }
            }
        }
    }
}