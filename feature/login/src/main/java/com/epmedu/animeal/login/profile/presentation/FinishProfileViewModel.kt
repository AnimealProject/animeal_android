package com.epmedu.animeal.login.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FinishProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val nameValidator: FirstnameValidator = FirstnameValidator
    private val surnameValidator: SurnameValidator = SurnameValidator
    private val emailValidator: EmailValidator = EmailValidator
    private val birthDateValidator: BirthDateValidator = BirthDateValidator

    var state by mutableStateOf(ProfileState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        loadProfile()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.FirstnameChanged -> {
                state = state.copy(name = event.name)
            }
            is ProfileEvent.SurnameChanged -> {
                state = state.copy(surname = event.surname)
            }
            is ProfileEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is ProfileEvent.BirthDateChanged -> {
                state = state.copy(
                    formattedBirthDate = event.birthDate.format(),
                    birthDateString = event.birthDate
                )
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
        state = state.copy(nameError = nameValidationResult.errorMessage)
        return nameValidationResult
    }

    private fun isValidSurname(): ValidationResult {
        val validationResult = surnameValidator.validate(state.surname)
        state = state.copy(surnameError = validationResult.errorMessage)
        return validationResult
    }

    private fun isValidEmail(): ValidationResult {
        val validationResult = emailValidator.validate(state.email)
        state = state.copy(emailError = validationResult.errorMessage)
        return validationResult
    }

    private fun isValidBirthDate(): ValidationResult {
        val validationResult = birthDateValidator.validate(state.formattedBirthDate)
        state = state.copy(birthDateError = validationResult.errorMessage)
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
        state = state.copy(
            nameError = isValidName().errorMessage,
            surnameError = isValidSurname().errorMessage,
            emailError = isValidEmail().errorMessage,
            birthDateError = isValidBirthDate().errorMessage
        )
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
                    validationEventChannel.send(ValidationEvent.Success)
                }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            profileRepository.getProfile()
                .flowOn(Dispatchers.IO)
                .collect {
                    state = state.copy(
                        name = it.firstName,
                        surname = it.lastName,
                        email = it.email,
                        birthDateString = it.birthDate,
                        formattedBirthDate = it.birthDate.format()
                    )
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