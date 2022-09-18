package com.epmedu.animeal.profile.data.mapper

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey

class AuthUserAttributesToIsPhoneVerifiedMapper {

    private val phoneNumberVerifiedAttributeKey = "phone_number_verified"

    fun map(attributes: List<AuthUserAttribute>): Boolean {
        return attributes.find { it.key == AuthUserAttributeKey.custom(phoneNumberVerifiedAttributeKey) }?.value.toBoolean()
    }
}