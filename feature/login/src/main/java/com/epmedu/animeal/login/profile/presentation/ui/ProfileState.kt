package com.epmedu.animeal.login.profile.presentation.ui

import com.epmedu.animeal.extensions.tryParseDate
import java.time.LocalDate

data class ProfileState(
    val name: String = "",
    val nameError: UiText? = null,
    val surname: String = "",
    val surnameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val birthDateString: String = "",
    val birthDateError: UiText? = null,
    val formattedBirthDate: String = "",
    val formattedPhoneNumber: String = ""
) {
    val initialDate: LocalDate
        get() {
            return if (birthDateString.isBlank()) LocalDate.now()
            else {
                tryParseDate(formattedBirthDate) ?: LocalDate.now()
            }
        }
    val phoneNumber: String
        get() = formattedPhoneNumber.replace("\\D".toRegex(), "")
}