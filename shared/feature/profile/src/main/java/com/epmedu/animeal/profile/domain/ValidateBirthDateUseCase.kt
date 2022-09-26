package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.BlankBirthDateError
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.ValidBirthDate
import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator
import com.epmedu.animeal.resources.R

class ValidateBirthDateUseCase(private val validator: ProfileValidator) {

    operator fun invoke(birthDate: String): UiText {
        return when (validator.validateBirthDate(birthDate)) {
            is ValidBirthDate -> {
                UiText.Empty
            }
            is BlankBirthDateError -> {
                UiText.StringResource(R.string.profile_select_birth_date)
            }
        }
    }
}