package com.epmedu.animeal.foundation.common.validation.result

sealed interface NameValidationResult {
    object ValidName : NameValidationResult
    object BlankNameError : NameValidationResult
    data class WrongNameLengthError(val requiredLength: IntRange) : NameValidationResult
}