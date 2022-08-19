package com.epmedu.animeal.signup.finishprofile.presentation

import java.time.LocalDate

internal sealed interface FinishProfileScreenEvent {
    data class NameChanged(val name: String) : FinishProfileScreenEvent
    data class SurnameChanged(val surname: String) : FinishProfileScreenEvent
    data class EmailChanged(val email: String) : FinishProfileScreenEvent
    data class BirthDateChanged(val birthDate: LocalDate) : FinishProfileScreenEvent
    object Submit : FinishProfileScreenEvent
    object ValidateName : FinishProfileScreenEvent
    object ValidateSurname : FinishProfileScreenEvent
    object ValidateEmail : FinishProfileScreenEvent
    object ValidateBirthDate : FinishProfileScreenEvent
}