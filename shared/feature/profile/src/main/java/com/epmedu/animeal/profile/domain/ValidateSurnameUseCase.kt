package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult.*
import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator
import com.epmedu.animeal.resources.R

class ValidateSurnameUseCase(private val validator: ProfileValidator) {

    operator fun invoke(surname: String): UiText {
        return when (val result = validator.validateSurname(surname)) {
            is ValidSurname -> {
                UiText.Empty
            }
            is BlankSurnameError -> {
                UiText.StringResource(R.string.profile_surname_blank_error_msg)
            }
            is WrongSurnameLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
            is InvalidSurnameError -> {
                UiText.StringResource(R.string.profile_surname_invalid_error_msg)
            }
        }
    }
}