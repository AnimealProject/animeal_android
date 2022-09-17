package com.epmedu.animeal.profile.presentation.mapper

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult
import com.epmedu.animeal.resources.R

class SurnameValidationResultToUiTextMapper {

    fun map(result: SurnameValidationResult): UiText {
        return when (result) {
            is SurnameValidationResult.ValidSurname -> {
                UiText.Empty
            }
            is SurnameValidationResult.BlankSurnameError -> {
                UiText.StringResource(R.string.profile_surname_blank_error_msg)
            }
            is SurnameValidationResult.WrongSurnameLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
        }
    }
}