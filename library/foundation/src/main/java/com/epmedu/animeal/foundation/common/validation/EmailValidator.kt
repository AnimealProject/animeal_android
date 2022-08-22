package com.epmedu.animeal.foundation.common.validation

import android.util.Patterns
import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.resources.R

internal object EmailValidator : Validator {
    private const val MIN_LENGTH = 7
    private const val MAX_LENGTH = 255

    override fun validate(value: String) = when {
        value.isBlank() -> {
            ValidationResult.Failure(
                errorMessage = UiText.StringResource(R.string.profile_email_blank_error_msg)
            )
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
        !isEmailValid(value) -> {
            ValidationResult.Failure(
                errorMessage = UiText.StringResource(R.string.profile_email_invalid_error_msg)
            )
        }
        else -> ValidationResult.Success
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}