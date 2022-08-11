package com.epmedu.animeal.login.profile.presentation

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.extensions.DAY_MONTH_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.common.validation.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FinishProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : StateViewModel<FinishProfileState>(initialState = FinishProfileState()) {

    private val validator: ProfileValidator = ProfileValidator()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> get() = _event.asSharedFlow()

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
                        formattedBirthDate = formatDateToString(
                            event.birthDate, DAY_MONTH_COMMA_YEAR_FORMATTER
                        )
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
            birthDate = state.formattedBirthDate
        )
        viewModelScope.launch {
            profileRepository.saveProfile(profile).collect {
                _event.emit(Event.Saved)
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

    sealed interface Event {
        object Saved : Event
    }
}