package com.epmedu.animeal.foundation.common.validation

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.resources.R

internal object BirthDateValidator : Validator {

    override fun validate(value: String) = when {
        value.isBlank() -> {
            ValidationResult.Failure(errorMessage = UiText.StringResource(R.string.profile_select_birth_date))
        }
        else -> ValidationResult.Success
    }
}