package com.epmedu.animeal.foundation.common.validation

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.resources.R

object SurnameValidator : Validator {

    @Suppress("ReturnCount")
    override fun validate(value: String): ValidationResult {
        if (value.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = UiText.StringResource(R.string.profile_surname_blank_error_msg)
            )
        }
        if (value.length !in 2..35) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = UiText.StringResource(R.string.profile_text_field_error_msg)
            )
        }
        return ValidationResult(isSuccess = true)
    }
}