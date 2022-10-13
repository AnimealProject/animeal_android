package com.epmedu.animeal.foundation.common.validation.validator

import com.epmedu.animeal.extensions.tryParseDate
import com.epmedu.animeal.foundation.common.validation.Constants.MIN_AGE
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.BlankBirthDateError
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.TooYoungBirthDateError
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.ValidBirthDate
import java.time.LocalDate

internal object BirthDateValidator {

    fun validate(value: String) = when {
        value.isBlank() -> BlankBirthDateError
        value.isTooYoung() -> TooYoungBirthDateError
        else -> ValidBirthDate
    }

    private fun String.isTooYoung() = tryParseDate(this)?.let {
        it.plusYears(MIN_AGE) > LocalDate.now()
    } ?: true
}