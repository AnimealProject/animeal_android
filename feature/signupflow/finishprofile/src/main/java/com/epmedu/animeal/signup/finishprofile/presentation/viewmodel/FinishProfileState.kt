package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import com.epmedu.animeal.foundation.common.UiText

internal data class FinishProfileState(
    val name: String = "",
    val nameError: UiText = UiText.Empty,
    val surname: String = "",
    val surnameError: UiText = UiText.Empty,
    val email: String = "",
    val emailError: UiText = UiText.Empty,
    val birthDateError: UiText = UiText.Empty,
    val formattedBirthDate: String = "",
    val formattedPhoneNumber: String = ""
) {
    val phoneNumber: String
        get() = formattedPhoneNumber.replace("\\D".toRegex(), "")

    fun hasErrors() =
        listOf(nameError, surnameError, emailError, birthDateError).any { it !is UiText.Empty }
}