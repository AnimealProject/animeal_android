package com.epmedu.animeal.more.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.DAY_MONTH_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.common.validation.ProfileValidator
import com.epmedu.animeal.foundation.common.validation.ValidationResult
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.BirthDateChanged
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.Discard
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.Edit
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.EmailChanged
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.NameChanged
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.Save
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.SurnameChanged
import com.epmedu.animeal.more.profile.presentation.viewmodel.ProfileState.FormState.EDITABLE
import com.epmedu.animeal.more.profile.presentation.viewmodel.ProfileState.FormState.EDITED
import com.epmedu.animeal.more.profile.presentation.viewmodel.ProfileState.FormState.READ_ONLY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val validator: ProfileValidator,
    private val profileRepository: ProfileRepository
) : ViewModel(),
    StateDelegate<ProfileState> by DefaultStateDelegate(initialState = ProfileState()) {

    private var lastSavedProfile = Profile()

    init {
        loadProfile()
        launchCheckingChanges()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            profileRepository.getProfile().collect {
                lastSavedProfile = it
                updateState { copy(profile = it) }
            }
        }
    }

    fun handleEvent(event: ProfileScreenEvent) {
        when (event) {
            is Edit -> updateState { copy(formState = EDITABLE) }
            is Discard -> discardChanges()
            is Save -> saveChanges()
            is NameChanged -> updateAndValidateName(event.name)
            is SurnameChanged -> updateAndValidateSurname(event.surname)
            is EmailChanged -> updateAndValidateEmail(event.email)
            is BirthDateChanged -> updateAndValidateBirthdate(event.birthDate)
        }
    }

    private fun launchCheckingChanges() {
        stateFlow.onEach {
            if (it.formState != READ_ONLY) {
                updateState {
                    copy(
                        formState =
                        if (it.hasErrors() || it.profile == lastSavedProfile) EDITABLE
                        else EDITED
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun discardChanges() {
        lastSavedProfile.let { profile ->
            updateState {
                copy(
                    profile = profile,
                    formState = READ_ONLY,
                    nameError = null,
                    surnameError = null,
                    emailError = null,
                    birthDateError = null
                )
            }
        }
    }

    private fun saveChanges() {
        viewModelScope.launch {
            profileRepository.saveProfile(state.profile).collectLatest {
                lastSavedProfile = state.profile
                updateState { copy(formState = READ_ONLY) }
            }
        }
    }

    private fun updateAndValidateName(name: String) {
        updateState {
            copy(
                profile = profile.copy(name = name),
                nameError = reduceValidationResult(validator.validateName(name))
            )
        }
    }

    private fun updateAndValidateSurname(surname: String) {
        updateState {
            copy(
                profile = profile.copy(surname = surname),
                surnameError = reduceValidationResult(validator.validateSurname(surname))
            )
        }
    }

    private fun updateAndValidateEmail(email: String) {
        updateState {
            copy(
                profile = profile.copy(email = email),
                emailError = reduceValidationResult(validator.validateEmail(email))
            )
        }
    }

    private fun updateAndValidateBirthdate(birthDate: LocalDate) {
        val formattedBirthDate = formatDateToString(
            date = birthDate,
            formatter = DAY_MONTH_COMMA_YEAR_FORMATTER
        )
        updateState {
            copy(
                profile = profile.copy(birthDate = formattedBirthDate),
                birthDateError = reduceValidationResult(
                    validator.validateBirthDate(formattedBirthDate)
                )
            )
        }
    }

    private fun reduceValidationResult(result: ValidationResult) = when (result) {
        is ValidationResult.Success -> null
        is ValidationResult.Failure -> result.errorMessage
    }
}