package com.epmedu.animeal.login.profile.domain

import com.epmedu.animeal.login.profile.presentation.ui.UiText
import com.epmedu.animeal.resources.R
import java.util.regex.Pattern

internal object EmailValidator : Validator {
    private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"

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
        return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
    }
}