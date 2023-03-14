package com.epmedu.animeal.networkuser.data.mapper

import com.amplifyframework.auth.AuthUserAttribute
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.auth.constants.UserAttributesKey.birthDateKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.emailKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.nameKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.phoneNumberKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.surnameKey
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.extensions.DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.DAY_MONTH_YEAR_SLASH_FORMATTER
import com.epmedu.animeal.extensions.MONTH_DAY_YEAR_SLASH_FORMATTER
import com.epmedu.animeal.extensions.reformatDateToString
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.domain.model.Region

class AuthUserAttributesToProfileMapper {

    fun map(
        attributes: List<AuthUserAttribute>,
        authenticationType: AuthenticationType
    ): Profile {
        val birthDate = attributes.find { it.key == birthDateKey }?.value
        val convertedLocalFormatDate = birthDate?.let {
            reformatDateToString(
                dateString = birthDate,
                originalFormatter = when (authenticationType) {
                    is AuthenticationType.Mobile -> DAY_MONTH_YEAR_SLASH_FORMATTER
                    is AuthenticationType.Facebook -> MONTH_DAY_YEAR_SLASH_FORMATTER
                },
                newFormatter = DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
            )
        } ?: ""
        val phoneNumberWithPrefix = attributes.find { it.key == phoneNumberKey }?.value
        val phoneNumberRegion = phoneNumberWithPrefix?.let {
            Region.values().find { phoneNumberWithPrefix.startsWith(it.phoneNumberCode) }
        } ?: Region.GE
        val phoneNumber =
            phoneNumberWithPrefix?.removePrefix(phoneNumberRegion.phoneNumberCode) ?: EMPTY_STRING
        return Profile(
            name = attributes.find { it.key == nameKey }?.value ?: "",
            surname = attributes.find { it.key == surnameKey }?.value ?: "",
            birthDate = convertedLocalFormatDate,
            phoneNumberRegion = phoneNumberRegion,
            phoneNumber = phoneNumber,
            email = attributes.find { it.key == emailKey }?.value ?: "",
        )
    }
}