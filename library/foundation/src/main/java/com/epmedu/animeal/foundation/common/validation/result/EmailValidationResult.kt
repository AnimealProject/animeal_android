package com.epmedu.animeal.foundation.common.validation.result

sealed interface EmailValidationResult {
    object ValidEmail : EmailValidationResult
    object BlankEmailError : EmailValidationResult
    object InvalidEmailError : EmailValidationResult
    data class WrongEmailLengthError(val requiredLength: IntRange) : EmailValidationResult
}
