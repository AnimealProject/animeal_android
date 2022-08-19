package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import com.epmedu.animeal.extensions.tryParseDate
import com.epmedu.animeal.foundation.common.UiText
import java.time.LocalDate

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
    val birthDate: LocalDate
        get() {
            return if (formattedBirthDate.isEmpty()) LocalDate.now()
            else tryParseDate(formattedBirthDate) ?: LocalDate.now()
        }

    val phoneNumber: String
        get() = formattedPhoneNumber.replace("\\D".toRegex(), "")

    fun hasErrors(): Boolean {
        return listOfNotNull(nameError, surnameError, emailError, birthDateError).isNotEmpty()
    }
}