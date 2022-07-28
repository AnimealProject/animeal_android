package com.epmedu.animeal.login.profile.presentation

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.foundation.common.validation.*
import com.epmedu.animeal.foundation.input.BirthDateFormatTransformation
import com.epmedu.animeal.login.profile.data.model.Profile
import com.epmedu.animeal.login.profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FinishProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : StateViewModel<FinishProfileState>(initialState = FinishProfileState()) {

    private val validator: ProfileValidator = ProfileValidator()

    private val _validationEvent = MutableSharedFlow<ValidationEvent>()
    val validationEvent: SharedFlow<ValidationEvent> get() = _validationEvent.asSharedFlow()

    init {
        loadProfile()
    }

    fun handleEvents(event: FinishProfileEvent) {
        when (event) {
            is FinishProfileEvent.NameChanged -> {
                updateState { copy(name = event.name) }
            }
            is FinishProfileEvent.SurnameChanged -> {
                updateState { copy(surname = event.surname) }
            }
            is FinishProfileEvent.EmailChanged -> {
                updateState { copy(email = event.email) }
            }
            is FinishProfileEvent.BirthDateChanged -> {
                updateState {
                    copy(
                        formattedBirthDate = event.birthDate.format(),
                        birthDateString = event.birthDate
                    )
                }
            }
            FinishProfileEvent.Submit -> {
                submitData()
            }
            FinishProfileEvent.ValidateName -> {
                updateState {
                    copy(nameError = validator.validateName(state.name).errorMessage)
                }
            }
            FinishProfileEvent.ValidateSurname -> {
                updateState {
                    copy(surnameError = validator.validateSurname(state.surname).errorMessage)
                }
            }
            FinishProfileEvent.ValidateEmail -> {
                updateState {
                    copy(emailError = validator.validateEmail(state.email).errorMessage)
                }
            }
            FinishProfileEvent.ValidateBirthDate -> {
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
            birthDate = state.birthDateString
        )
        viewModelScope.launch {
            profileRepository.saveProfile(profile).collect {
                _validationEvent.emit(ValidationEvent.Success)
            }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            profileRepository.getProfile()
                .flowOn(Dispatchers.IO)
                .collect {
                    updateState {
                        copy(
                            name = it.firstName,
                            surname = it.lastName,
                            email = it.email,
                            birthDateString = it.birthDate,
                            formattedBirthDate = it.birthDate.format(),
                            formattedPhoneNumber = it.phoneNumber
                        )
                    }
                }
        }
    }

    sealed interface ValidationEvent {
        object Success : ValidationEvent
    }

    private fun String.format(): String {
        return BirthDateFormatTransformation.filter(AnnotatedString(this)).text.text
    }
}