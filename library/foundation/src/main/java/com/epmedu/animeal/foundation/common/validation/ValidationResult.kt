package com.epmedu.animeal.foundation.common.validation

import com.epmedu.animeal.foundation.common.UiText

sealed interface ValidationResult {
    object Success : ValidationResult
    data class Failure(val errorMessage: UiText?) : ValidationResult
}