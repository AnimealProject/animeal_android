package com.epmedu.animeal.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.profile.domain.ValidateBirthDateUseCase
import com.epmedu.animeal.profile.domain.ValidateEmailUseCase
import com.epmedu.animeal.profile.domain.ValidateNameUseCase
import com.epmedu.animeal.profile.domain.ValidateSurnameUseCase
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.BirthDateChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.EmailChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.NameChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.PhoneNumberChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.RegionChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.SurnameChanged
import java.time.LocalDate

abstract class BaseProfileViewModel(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateSurnameUseCase: ValidateSurnameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase
) : ViewModel(),
    StateDelegate<ProfileState> by DefaultStateDelegate(initialState = ProfileState()) {

    fun handleInputFormEvent(event: ProfileInputFormEvent) {
        when (event) {
            is NameChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(name = event.name),
                        nameError = validateNameUseCase(event.name)
                    )
                }
            }
            is SurnameChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(surname = event.surname),
                        surnameError = validateSurnameUseCase(event.surname)
                    )
                }
            }
            is EmailChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(email = event.email),
                        emailError = validateEmailUseCase(event.email)
                    )
                }
            }
            is PhoneNumberChanged -> {
                handlePhoneNumberChangedEvent(event)
            }
            is RegionChanged -> {
                updateState {
                    copy(
                        profile = profile.copy(phoneNumberRegion = event.region, phoneNumber = ""),
                    )
                }
            }
            is BirthDateChanged -> {
                val formattedBirthDate = formatBirthDate(event.birthDate)
                updateState {
                    copy(
                        profile = profile.copy(birthDate = formattedBirthDate),
                        birthDateError = validateBirthDateUseCase(formattedBirthDate)
                    )
                }
            }
        }
    }

    protected abstract fun handlePhoneNumberChangedEvent(event: PhoneNumberChanged)

    private fun formatBirthDate(birthDate: LocalDate) = formatDateToString(
        date = birthDate,
        formatter = DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
    )
}