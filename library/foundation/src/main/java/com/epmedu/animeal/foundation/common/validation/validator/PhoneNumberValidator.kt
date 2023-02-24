package com.epmedu.animeal.foundation.common.validation.validator

import android.telephony.PhoneNumberUtils
import com.epmedu.animeal.foundation.common.validation.result.PhoneNumberValidationResult.TooShortPhoneNumberError
import com.epmedu.animeal.foundation.common.validation.result.PhoneNumberValidationResult.ValidPhoneNumber

internal object PhoneNumberValidator {
    fun validate(value: String, phoneNumberDigitsCount: IntArray) = when {
        !value.isValidPhoneNumber(phoneNumberDigitsCount) -> TooShortPhoneNumberError
        else -> ValidPhoneNumber
    }

    private fun String.isValidPhoneNumber(phoneNumberDigitsCount: IntArray): Boolean {
        return phoneNumberDigitsCount.contains(length) && PhoneNumberUtils.isGlobalPhoneNumber(this)
    }
}