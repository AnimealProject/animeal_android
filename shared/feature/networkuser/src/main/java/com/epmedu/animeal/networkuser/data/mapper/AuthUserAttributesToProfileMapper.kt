package com.epmedu.animeal.networkuser.data.mapper

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.epmedu.animeal.extensions.DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.MONTH_DAY_YEAR_SLASH_FORMATTER
import com.epmedu.animeal.extensions.reformatDateToString
import com.epmedu.animeal.foundation.common.validation.Constants.GE_PHONE_NUMBER_LENGTH
import com.epmedu.animeal.profile.data.model.Profile

class AuthUserAttributesToProfileMapper {

    fun map(attributes: List<AuthUserAttribute>): Profile {
        val birthDate = attributes.find { it.key == AuthUserAttributeKey.birthdate() }?.value
        val convertedLocalFormatDate = birthDate?.let {
            reformatDateToString(
                dateString = birthDate,
                originalFormatter = MONTH_DAY_YEAR_SLASH_FORMATTER,
                newFormatter = DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
            )
        } ?: ""
        return Profile(
            name = attributes.find { it.key == AuthUserAttributeKey.givenName() }?.value ?: "",
            surname = attributes.find { it.key == AuthUserAttributeKey.familyName() }?.value ?: "",
            birthDate = convertedLocalFormatDate,
            phoneNumber = attributes.find { it.key == AuthUserAttributeKey.phoneNumber() }?.value
                ?.takeLast(GE_PHONE_NUMBER_LENGTH) ?: "",
            email = attributes.find { it.key == AuthUserAttributeKey.email() }?.value ?: ""
        )
    }
}