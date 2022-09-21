package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.BlankEmailError
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.InvalidEmailError
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.ValidEmail
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.WrongEmailLengthError
import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator
import com.epmedu.animeal.resources.R

class ValidateEmailUseCase(private val validator: ProfileValidator) {

    operator fun invoke(email: String): UiText {
        return when (val result = validator.validateEmail(email)) {
            is ValidEmail -> {
                UiText.Empty
            }
            is BlankEmailError -> {
                UiText.StringResource(R.string.profile_email_blank_error_msg)
            }
            is WrongEmailLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
            is InvalidEmailError -> {
                UiText.StringResource(R.string.profile_email_invalid_error_msg)
            }
        }
    }
}