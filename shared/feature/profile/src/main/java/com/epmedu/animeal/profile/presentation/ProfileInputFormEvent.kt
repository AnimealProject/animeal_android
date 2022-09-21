package com.epmedu.animeal.profile.presentation

import java.time.LocalDate

sealed interface ProfileInputFormEvent {
    data class NameChanged(val name: String) : ProfileInputFormEvent
    data class SurnameChanged(val surname: String) : ProfileInputFormEvent
    data class EmailChanged(val email: String) : ProfileInputFormEvent
    data class PhoneNumberChanged(val phoneNumber: String) : ProfileInputFormEvent
    data class BirthDateChanged(val birthDate: LocalDate) : ProfileInputFormEvent
}