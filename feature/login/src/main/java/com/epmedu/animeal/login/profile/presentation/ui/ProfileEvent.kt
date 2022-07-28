package com.epmedu.animeal.login.profile.presentation.ui

sealed interface ProfileEvent {
    data class FirstnameChanged(val name: String) : ProfileEvent
    data class SurnameChanged(val surname: String) : ProfileEvent
    data class EmailChanged(val email: String) : ProfileEvent
    data class BirthDateChanged(val birthDate: String) : ProfileEvent
    object Submit : ProfileEvent
    object FirstnameValidation : ProfileEvent
    object SurnameValidation : ProfileEvent
    object EmailValidation : ProfileEvent
    object BirthDateValidation : ProfileEvent
}