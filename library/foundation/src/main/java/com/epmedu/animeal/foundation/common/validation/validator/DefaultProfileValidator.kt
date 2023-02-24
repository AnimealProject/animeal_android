package com.epmedu.animeal.foundation.common.validation.validator

class DefaultProfileValidator : ProfileValidator {
    override fun validateName(name: String) = NameValidator.validate(name)

    override fun validateSurname(surname: String) = SurnameValidator.validate(surname)

    override fun validateEmail(email: String) = EmailValidator.validate(email)

    override fun validatePhoneNumber(phoneNumber: String, phoneNumberDigitsCount: IntArray) =
        PhoneNumberValidator.validate(phoneNumber, phoneNumberDigitsCount)

    override fun validateBirthDate(birthDate: String) = BirthDateValidator.validate(birthDate)
}