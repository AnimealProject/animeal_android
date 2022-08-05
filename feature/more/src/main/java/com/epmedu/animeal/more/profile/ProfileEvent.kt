package com.epmedu.animeal.more.profile

import java.time.LocalDate

internal sealed interface ProfileEvent {
    data class NameChanged(val name: String) : ProfileEvent
    data class SurnameChanged(val surname: String) : ProfileEvent
    data class EmailChanged(val email: String) : ProfileEvent
    data class BirthDateChanged(val birthDate: LocalDate) : ProfileEvent
    object Edit : ProfileEvent
    object Discard : ProfileEvent
    object Save : ProfileEvent
    object ValidateName : ProfileEvent
    object ValidateSurname : ProfileEvent
    object ValidateEmail : ProfileEvent
    object ValidateBirthDate : ProfileEvent
}