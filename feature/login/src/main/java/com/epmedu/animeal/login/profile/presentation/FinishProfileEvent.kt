package com.epmedu.animeal.login.profile.presentation

internal sealed interface FinishProfileEvent {
    data class NameChanged(val name: String) : FinishProfileEvent
    data class SurnameChanged(val surname: String) : FinishProfileEvent
    data class EmailChanged(val email: String) : FinishProfileEvent
    data class BirthDateChanged(val birthDate: String) : FinishProfileEvent
    object Submit : FinishProfileEvent
    object Cancel : FinishProfileEvent
    object ValidateName : FinishProfileEvent
    object ValidateSurname : FinishProfileEvent
    object ValidateEmail : FinishProfileEvent
    object ValidateBirthDate : FinishProfileEvent
}