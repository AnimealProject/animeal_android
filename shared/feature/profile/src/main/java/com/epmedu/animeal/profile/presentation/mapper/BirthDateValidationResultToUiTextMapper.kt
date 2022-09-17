package com.epmedu.animeal.profile.presentation.mapper

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.BlankBirthDateError
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.ValidBirthDate
import com.epmedu.animeal.resources.R

class BirthDateValidationResultToUiTextMapper {

    fun map(result: BirthDateValidationResult): UiText {
        return when (result) {
            is ValidBirthDate -> {
                UiText.Empty
            }
            is BlankBirthDateError -> {
                UiText.StringResource(R.string.profile_select_birth_date)
            }
        }
    }
}