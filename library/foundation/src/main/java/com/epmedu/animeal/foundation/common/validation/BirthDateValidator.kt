package com.epmedu.animeal.foundation.common.validation

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.resources.R

object BirthDateValidator : Validator {

    override fun validate(value: String): ValidationResult {
        return when {
            value.isBlank() -> {
                ValidationResult(
                    isSuccess = false,
                    errorMessage = UiText.StringResource(R.string.profile_select_birth_date)
                )
            }
            else -> ValidationResult(isSuccess = true)
        }
    }
}