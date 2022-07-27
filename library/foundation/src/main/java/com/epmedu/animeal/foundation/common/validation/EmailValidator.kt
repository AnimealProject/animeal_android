package com.epmedu.animeal.foundation.common.validation

import android.util.Patterns
import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.resources.R

object EmailValidator : Validator {

    @Suppress("ReturnCount")
    override fun validate(value: String): ValidationResult {
        if (value.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = UiText.StringResource(R.string.profile_email_blank_error_msg)
            )
        }
        if (!isEmailValid(value)) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = UiText.StringResource(R.string.profile_email_invalid_error_msg)
            )
        }
        return ValidationResult(isSuccess = true)
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}