package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult.BlankNameError
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult.ValidName
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult.WrongNameLengthError
import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator
import com.epmedu.animeal.resources.R

class ValidateNameUseCase(private val validator: ProfileValidator) {

    operator fun invoke(name: String): UiText {
        return when (val result = validator.validateName(name)) {
            is ValidName -> {
                UiText.Empty
            }
            is BlankNameError -> {
                UiText.StringResource(R.string.profile_name_blank_error_msg)
            }
            is WrongNameLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
        }
    }
}