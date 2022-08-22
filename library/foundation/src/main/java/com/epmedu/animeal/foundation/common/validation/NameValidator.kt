package com.epmedu.animeal.foundation.common.validation

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.resources.R

internal object NameValidator : Validator {
    private const val MIN_LENGTH = 2
    private const val MAX_LENGTH = 35

    override fun validate(value: String) = when {
        value.isBlank() -> {
            ValidationResult.Failure(errorMessage = UiText.StringResource(R.string.profile_name_blank_error_msg))
        }
        value.length !in MIN_LENGTH..MAX_LENGTH -> {
            ValidationResult.Failure(
                errorMessage = UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    MIN_LENGTH,
                    MAX_LENGTH
                )
            )
        }
        else -> ValidationResult.Success
    }
}