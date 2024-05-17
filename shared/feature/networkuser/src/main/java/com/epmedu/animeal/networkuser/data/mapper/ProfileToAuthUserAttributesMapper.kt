package com.epmedu.animeal.networkuser.data.mapper

import com.amplifyframework.auth.AuthUserAttribute
import com.epmedu.animeal.auth.constants.UserAttributesKey.emailKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.nameKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.phoneNumberKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.surnameKey
import com.epmedu.animeal.profile.data.model.Profile

class ProfileToAuthUserAttributesMapper {

    fun map(profile: Profile): List<AuthUserAttribute> {
        return listOf(
            AuthUserAttribute(nameKey, profile.name),
            AuthUserAttribute(surnameKey, profile.surname),
            AuthUserAttribute(emailKey, profile.email),
            AuthUserAttribute(
                phoneNumberKey,
                profile.phoneNumberRegion.phoneNumberCode + profile.phoneNumber
            )
        )
    }
}