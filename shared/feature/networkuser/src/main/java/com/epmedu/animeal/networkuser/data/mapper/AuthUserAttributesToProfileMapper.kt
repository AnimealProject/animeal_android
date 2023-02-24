package com.epmedu.animeal.networkuser.data.mapper

import com.amplifyframework.auth.AuthUserAttribute
import com.epmedu.animeal.auth.constants.UserAttributesKey.birthDateKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.emailKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.nameKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.phoneNumberKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.surnameKey
import com.epmedu.animeal.extensions.DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.DAY_MONTH_YEAR_SLASH_FORMATTER
import com.epmedu.animeal.extensions.reformatDateToString
import com.epmedu.animeal.foundation.common.validation.Constants.GE_PHONE_NUMBER_LENGTH
import com.epmedu.animeal.profile.data.model.Profile

class AuthUserAttributesToProfileMapper {

    fun map(attributes: List<AuthUserAttribute>): Profile {
        val birthDate = attributes.find { it.key == birthDateKey }?.value
        val convertedLocalFormatDate = birthDate?.let {
            reformatDateToString(
                dateString = birthDate,
                originalFormatter = DAY_MONTH_YEAR_SLASH_FORMATTER,
                newFormatter = DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
            )
        } ?: ""
        return Profile(
            name = attributes.find { it.key == nameKey }?.value ?: "",
            surname = attributes.find { it.key == surnameKey }?.value ?: "",
            birthDate = convertedLocalFormatDate,
            phoneNumber = attributes.find { it.key == phoneNumberKey }?.value
                ?.takeLast(GE_PHONE_NUMBER_LENGTH) ?: "",
            email = attributes.find { it.key == emailKey }?.value ?: ""
        )
    }
}