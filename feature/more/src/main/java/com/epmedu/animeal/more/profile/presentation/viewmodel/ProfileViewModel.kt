package com.epmedu.animeal.more.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.DAY_MONTH_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.BlankBirthDateError
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.ValidBirthDate
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.BlankEmailError
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.InvalidEmailError
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.ValidEmail
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.WrongEmailLengthError
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult.BlankNameError
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult.ValidName
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult.WrongNameLengthError
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult.BlankSurnameError
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult.ValidSurname
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult.WrongSurnameLengthError
import com.epmedu.animeal.more.profile.domain.GetProfileUseCase
import com.epmedu.animeal.more.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.more.profile.domain.ValidateBirthDateUseCase
import com.epmedu.animeal.more.profile.domain.ValidateEmailUseCase
import com.epmedu.animeal.more.profile.domain.ValidateNameUseCase
import com.epmedu.animeal.more.profile.domain.ValidateSurnameUseCase
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
import com.epmedu.animeal.resources.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateSurnameUseCase: ValidateSurnameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase
) : ViewModel(),
    StateDelegate<ProfileState> by DefaultStateDelegate(initialState = ProfileState()) {

    private var lastSavedProfile = Profile()

    init {
        loadProfile()
        launchCheckingChanges()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            getProfileUseCase.execute().collect {
                lastSavedProfile = it
                updateState { copy(profile = it) }
            }
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

    fun handleEvent(event: ProfileScreenEvent) {
        when (event) {
            is Edit -> {
                updateState { copy(formState = EDITABLE) }
            }
            is Discard -> {
                updateState { ProfileState(profile = lastSavedProfile) }
            }
            is Save -> {
                saveChanges()
            }
            is NameChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(name = event.name),
                        nameError = validateName(event.name)
                    )
                }
            }
            is SurnameChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(surname = event.surname),
                        surnameError = validateSurname(event.surname)
                    )
                }
            }
            is EmailChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(email = event.email),
                        emailError = validateEmail(event.email)
                    )
                }
            }
            is BirthDateChanged -> {
                val formattedBirthDate = formatBirthDate(event.birthDate)
                updateState {
                    copy(
                        profile = profile.copy(birthDate = formattedBirthDate),
                        birthDateError = validateBirthdate(formattedBirthDate)
                    )
                }
            }
        }
    }

    private fun saveChanges() {
        viewModelScope.launch {
            saveProfileUseCase.execute(state.profile).collectLatest {
                lastSavedProfile = state.profile
                updateState { copy(formState = READ_ONLY) }
            }
        }
    }

    private fun validateName(name: String): UiText {
        return when (val result = validateNameUseCase.execute(name)) {
            is ValidName -> {
                UiText.Empty
            }
            is BlankNameError -> {
                UiText.StringResource(R.string.profile_name_blank_error_msg)
            }
            is WrongNameLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
        }
    }

    private fun validateSurname(surname: String): UiText {
        return when (val result = validateSurnameUseCase.execute(surname)) {
            is ValidSurname -> {
                UiText.Empty
            }
            is BlankSurnameError -> {
                UiText.StringResource(R.string.profile_surname_blank_error_msg)
            }
            is WrongSurnameLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
        }
    }

    private fun validateEmail(email: String): UiText {
        return when (val result = validateEmailUseCase.execute(email)) {
            is ValidEmail -> {
                UiText.Empty
            }
            is BlankEmailError -> {
                UiText.StringResource(R.string.profile_email_blank_error_msg)
            }
            is WrongEmailLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
            is InvalidEmailError -> {
                UiText.StringResource(R.string.profile_email_invalid_error_msg)
            }
        }
    }

    private fun formatBirthDate(birthDate: LocalDate) = formatDateToString(
        date = birthDate,
        formatter = DAY_MONTH_COMMA_YEAR_FORMATTER
    )

    private fun validateBirthdate(birthDate: String): UiText {
        return when (validateBirthDateUseCase.execute(birthDate)) {
            is ValidBirthDate -> {
                UiText.Empty
            }
            is BlankBirthDateError -> {
                UiText.StringResource(R.string.profile_select_birth_date)
            }
        }
    }
}