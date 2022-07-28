package com.epmedu.animeal.foundation.common.validation

import com.epmedu.animeal.foundation.common.UiText

data class ValidationResult(
    val isSuccess: Boolean,
    val errorMessage: UiText? = null
)