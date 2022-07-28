package com.epmedu.animeal.more.profile

import com.epmedu.animeal.extensions.tryParseDate
import com.epmedu.animeal.foundation.common.UiText
import java.time.LocalDate

data class ProfileState(
    val readonly: Boolean = true,
    val name: String = "",
    val nameError: UiText? = null,
    val surname: String = "",
    val surnameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val birthDate: String = "",
    val birthDateError: UiText? = null,
    val formattedBirthDate: String = "",
    val formattedPhoneNumber: String = ""
) {
    val initialDate: LocalDate
        get() {
            return if (birthDate.isBlank()) LocalDate.now()
            else tryParseDate(formattedBirthDate) ?: LocalDate.now()
        }

    val phoneNumber: String
        get() = formattedPhoneNumber.replace("\\D".toRegex(), "")

    fun hasErrors(): Boolean {
        return listOfNotNull(nameError, surnameError, emailError, birthDateError).isNotEmpty()
    }
}