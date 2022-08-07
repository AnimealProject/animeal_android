package com.epmedu.animeal.foundation.common.validation

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.resources.R

object SurnameValidator : Validator {
    private val requiredLength = 2..35

    override fun validate(value: String): ValidationResult {
        return when {
            value.isBlank() -> {
                ValidationResult(
                    isSuccess = false,
                    errorMessage = UiText.StringResource(R.string.profile_surname_blank_error_msg)
                )
            }
            value.length !in requiredLength -> {
                ValidationResult(
                    isSuccess = false,
                    errorMessage = UiText.StringResource(R.string.profile_text_field_error_msg)
                )
            }
            else -> ValidationResult(isSuccess = true)
        }
    }
}