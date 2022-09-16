package com.epmedu.animeal.foundation.common.validation.validator

import android.util.Patterns
import com.epmedu.animeal.foundation.common.validation.Constants.MAX_EMAIL_LENGTH
import com.epmedu.animeal.foundation.common.validation.Constants.MIN_EMAIL_LENGTH
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.BlankEmailError
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.InvalidEmailError
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.ValidEmail
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult.WrongEmailLengthError

internal object EmailValidator {
    private val requiredLength = MIN_EMAIL_LENGTH..MAX_EMAIL_LENGTH

    fun validate(value: String) = when {
        value.isBlank() -> BlankEmailError
        value.length !in requiredLength -> WrongEmailLengthError(requiredLength)
        !isEmailValid(value) -> InvalidEmailError
        else -> ValidEmail
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}