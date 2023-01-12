package com.epmedu.animeal.profile.data.mapper

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberVerifiedKey

class AuthUserAttributesToIsPhoneVerifiedMapper {

    fun map(attributes: List<AuthUserAttribute>): Boolean {
        return attributes.find { it.key == AuthUserAttributeKey.custom(phoneNumberVerifiedKey.name) }?.value.toBoolean()
    }
}