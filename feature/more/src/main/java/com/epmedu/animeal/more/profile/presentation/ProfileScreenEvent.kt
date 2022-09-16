package com.epmedu.animeal.more.profile.presentation

import java.time.LocalDate

internal sealed interface ProfileScreenEvent {
    object Edit : ProfileScreenEvent
    object Discard : ProfileScreenEvent
    object Save : ProfileScreenEvent
    data class NameChanged(val name: String) : ProfileScreenEvent
    data class SurnameChanged(val surname: String) : ProfileScreenEvent
    data class EmailChanged(val email: String) : ProfileScreenEvent
    data class BirthDateChanged(val birthDate: LocalDate) : ProfileScreenEvent
}