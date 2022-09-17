package com.epmedu.animeal.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.DAY_MONTH_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.profile.domain.ValidateBirthDateUseCase
import com.epmedu.animeal.profile.domain.ValidateEmailUseCase
import com.epmedu.animeal.profile.domain.ValidateNameUseCase
import com.epmedu.animeal.profile.domain.ValidateSurnameUseCase
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.BirthDateChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.EmailChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.NameChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.PhoneNumberChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.SurnameChanged
import com.epmedu.animeal.profile.presentation.mapper.BirthDateValidationResultToUiTextMapper
import com.epmedu.animeal.profile.presentation.mapper.EmailValidationResultToUiTextMapper
import com.epmedu.animeal.profile.presentation.mapper.NameValidationResultToUiTextMapper
import com.epmedu.animeal.profile.presentation.mapper.SurnameValidationResultToUiTextMapper
import java.time.LocalDate

@Suppress("LongParameterList")
abstract class BaseProfileViewModel(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateSurnameUseCase: ValidateSurnameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase,
    private val nameValidationResultToUiTextMapper: NameValidationResultToUiTextMapper,
    private val surnameValidationResultToUiTextMapper: SurnameValidationResultToUiTextMapper,
    private val emailValidationResultToUiTextMapper: EmailValidationResultToUiTextMapper,
    private val birthDateValidationResultToUiTextMapper: BirthDateValidationResultToUiTextMapper
) : ViewModel(),
    StateDelegate<ProfileState> by DefaultStateDelegate(initialState = ProfileState()) {

    fun handleInputFormEvent(event: ProfileInputFormEvent) {
        when (event) {
            is NameChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(name = event.name),
                        nameError = validateAndMapNameToError(event.name)
                    )
                }
            }
            is SurnameChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(surname = event.surname),
                        surnameError = validateAndMapSurnameToError(event.surname)
                    )
                }
            }
            is EmailChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(email = event.email),
                        emailError = validateAndMapEmailToError(event.email)
                    )
                }
            }
            is PhoneNumberChanged -> {
                handlePhoneNumberChangedEvent(event)
            }
            is BirthDateChanged -> {
                val formattedBirthDate = formatBirthDate(event.birthDate)
                updateState {
                    copy(
                        profile = profile.copy(birthDate = formattedBirthDate),
                        birthDateError = validateAndMapBirthDateToError(formattedBirthDate)
                    )
                }
            }
        }
    }

    protected abstract fun handlePhoneNumberChangedEvent(event: PhoneNumberChanged)

    protected fun validateAndMapNameToError(name: String): UiText {
        val validationResult = validateNameUseCase.execute(name)
        return nameValidationResultToUiTextMapper.map(validationResult)
    }

    protected fun validateAndMapSurnameToError(surname: String): UiText {
        val validationResult = validateSurnameUseCase.execute(surname)
        return surnameValidationResultToUiTextMapper.map(validationResult)
    }

    protected fun validateAndMapEmailToError(email: String): UiText {
        val validationResult = validateEmailUseCase.execute(email)
        return emailValidationResultToUiTextMapper.map(validationResult)
    }

    protected fun validateAndMapBirthDateToError(birthDate: String): UiText {
        val validationResult = validateBirthDateUseCase.execute(birthDate)
        return birthDateValidationResultToUiTextMapper.map(validationResult)
    }

    private fun formatBirthDate(birthDate: LocalDate) = formatDateToString(
        date = birthDate,
        formatter = DAY_MONTH_COMMA_YEAR_FORMATTER
    )
}