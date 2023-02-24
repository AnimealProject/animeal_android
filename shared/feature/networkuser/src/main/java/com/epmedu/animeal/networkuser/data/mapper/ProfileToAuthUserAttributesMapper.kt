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
import com.epmedu.animeal.profile.data.model.Profile

class ProfileToAuthUserAttributesMapper {

    fun map(profile: Profile): List<AuthUserAttribute> {
        val convertedAmplifyFormatDate = reformatDateToString(
            dateString = profile.birthDate,
            originalFormatter = DAY_MONTH_NAME_COMMA_YEAR_FORMATTER,
            newFormatter = DAY_MONTH_YEAR_SLASH_FORMATTER
        )
        return listOf(
            AuthUserAttribute(nameKey, profile.name),
            AuthUserAttribute(surnameKey, profile.surname),
            AuthUserAttribute(emailKey, profile.email),
            AuthUserAttribute(birthDateKey, convertedAmplifyFormatDate),
            AuthUserAttribute(
                phoneNumberKey,
                profile.phoneNumberRegion.phoneNumberCode + profile.phoneNumber
            )
        )
    }
}