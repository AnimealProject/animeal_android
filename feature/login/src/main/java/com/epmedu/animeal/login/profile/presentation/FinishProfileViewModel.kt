package com.epmedu.animeal.login.profile.presentation

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.foundation.input.BirthDateFormatTransformation
import com.epmedu.animeal.login.profile.data.model.Profile
import com.epmedu.animeal.login.profile.data.repository.ProfileRepository
import com.epmedu.animeal.login.profile.domain.BirthDateValidator
import com.epmedu.animeal.login.profile.domain.EmailValidator
import com.epmedu.animeal.login.profile.domain.FirstnameValidator
import com.epmedu.animeal.login.profile.domain.SurnameValidator
import com.epmedu.animeal.login.profile.domain.ValidationResult
import com.epmedu.animeal.login.profile.presentation.ui.ProfileEvent
import com.epmedu.animeal.login.profile.presentation.ui.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : StateViewModel<ProfileState>(initialState = ProfileState()) {
    private val nameValidator: FirstnameValidator = FirstnameValidator
    private val surnameValidator: SurnameValidator = SurnameValidator
    private val emailValidator: EmailValidator = EmailValidator
    private val birthDateValidator: BirthDateValidator = BirthDateValidator

    private val _validationSharedFlow = MutableSharedFlow<ValidationEvent>()
    val validationSharedFlow: SharedFlow<ValidationEvent> get() = _validationSharedFlow.asSharedFlow()

    init {
        loadProfile()
    }

    fun handleEvents(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.FirstnameChanged -> {
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
                        formattedBirthDate = event.birthDate.format(),
                        birthDateString = event.birthDate
                    )
                }
            }
            ProfileEvent.Submit -> {
                submitData()
            }
            ProfileEvent.FirstnameValidation -> {
                isValidName()
            }
            ProfileEvent.SurnameValidation -> {
                isValidSurname()
            }
            ProfileEvent.EmailValidation -> {
                isValidEmail()
            }
            ProfileEvent.BirthDateValidation -> {
                isValidBirthDate()
            }
        }
    }

    private fun isValidName(): ValidationResult {
        val nameValidationResult = nameValidator.validate(state.name)
        updateState {
            copy(nameError = nameValidationResult.errorMessage)
        }
        return nameValidationResult
    }

    private fun isValidSurname(): ValidationResult {
        val validationResult = surnameValidator.validate(state.surname)
        updateState {
            copy(surnameError = validationResult.errorMessage)
        }
        return validationResult
    }

    private fun isValidEmail(): ValidationResult {
        val validationResult = emailValidator.validate(state.email)
        updateState {
            copy(emailError = validationResult.errorMessage)
        }
        return validationResult
    }

    private fun isValidBirthDate(): ValidationResult {
        val validationResult = birthDateValidator.validate(state.formattedBirthDate)
        updateState {
            copy(birthDateError = validationResult.errorMessage)
        }
        return validationResult
    }

    private fun submitData() {
        val hasError: Boolean =
            listOf(
                isValidName(),
                isValidSurname(),
                isValidEmail(),
                isValidBirthDate()
            ).any { !it.isSuccess }
        updateState {
            copy(
                nameError = isValidName().errorMessage,
                surnameError = isValidSurname().errorMessage,
                emailError = isValidEmail().errorMessage,
                birthDateError = isValidBirthDate().errorMessage
            )
        }
        if (hasError) {
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
            profileRepository.saveProfile(profile)
                .collect {
                    _validationSharedFlow.emit(ValidationEvent.Success)
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