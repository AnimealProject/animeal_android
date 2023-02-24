package com.epmedu.animeal.foundation.common.validation.validator

import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult
import com.epmedu.animeal.foundation.common.validation.result.PhoneNumberValidationResult
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult

interface ProfileValidator {
    fun validateName(name: String): NameValidationResult
    fun validateSurname(surname: String): SurnameValidationResult
    fun validateEmail(email: String): EmailValidationResult
    fun validatePhoneNumber(phoneNumber: String, phoneNumberDigitsCount: IntArray): PhoneNumberValidationResult
    fun validateBirthDate(birthDate: String): BirthDateValidationResult
}