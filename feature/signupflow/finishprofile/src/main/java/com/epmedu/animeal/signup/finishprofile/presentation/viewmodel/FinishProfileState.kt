package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import com.epmedu.animeal.foundation.common.UiText

internal data class FinishProfileState(
    val name: String = "",
    val nameError: UiText? = null,
    val surname: String = "",
    val surnameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val birthDateError: UiText? = null,
    val formattedBirthDate: String = "",
    val formattedPhoneNumber: String = ""
) {
    val phoneNumber: String
        get() = formattedPhoneNumber.replace("\\D".toRegex(), "")

    fun hasErrors(): Boolean {
        return listOfNotNull(nameError, surnameError, emailError, birthDateError).isNotEmpty()
    }
}