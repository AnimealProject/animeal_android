package com.epmedu.animeal.networkuser.data.mapper

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.epmedu.animeal.extensions.*
import com.epmedu.animeal.profile.data.model.Profile

class ProfileToAuthUserAttributesMapper {

    fun map(profile: Profile): List<AuthUserAttribute> {
        val convertedAmplifyFormatDate = reformatDateToString(
            dateString = profile.birthDate,
            originalFormatter = DAY_MONTH_NAME_COMMA_YEAR_FORMATTER,
            newFormatter = MONTH_DAY_YEAR_SLASH_FORMATTER
        )
        return listOf(
            AuthUserAttribute(AuthUserAttributeKey.givenName(), profile.name),
            AuthUserAttribute(AuthUserAttributeKey.familyName(), profile.surname),
            AuthUserAttribute(AuthUserAttributeKey.email(), profile.email),
            AuthUserAttribute(AuthUserAttributeKey.birthdate(), convertedAmplifyFormatDate),
            AuthUserAttribute(AuthUserAttributeKey.phoneNumber(), profile.phoneNumberPrefix + profile.phoneNumber)
        )
    }
}