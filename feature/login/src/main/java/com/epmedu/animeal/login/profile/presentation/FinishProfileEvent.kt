package com.epmedu.animeal.login.profile.presentation

import java.time.LocalDate

internal sealed interface FinishProfileEvent {
    data class NameChanged(val name: String) : FinishProfileEvent
    data class SurnameChanged(val surname: String) : FinishProfileEvent
    data class EmailChanged(val email: String) : FinishProfileEvent
    data class BirthDateChanged(val birthDate: LocalDate) : FinishProfileEvent
    object Submit : FinishProfileEvent
    object ValidateName : FinishProfileEvent
    object ValidateSurname : FinishProfileEvent
    object ValidateEmail : FinishProfileEvent
    object ValidateBirthDate : FinishProfileEvent
}