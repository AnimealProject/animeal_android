package com.epmedu.animeal.auth.constants

import com.amplifyframework.auth.AuthUserAttributeKey

object UserAttributesKey {
    const val PHONE_NUMBER_VERIFIED_KEY = "phone_number_verified"
    const val IS_TRUSTED_ATTRIBUTE_KEY = "custom:trusted"

    val nameKey: AuthUserAttributeKey = AuthUserAttributeKey.name()
    val surnameKey: AuthUserAttributeKey = AuthUserAttributeKey.familyName()
    val phoneNumberKey: AuthUserAttributeKey = AuthUserAttributeKey.phoneNumber()
    val emailKey: AuthUserAttributeKey = AuthUserAttributeKey.email()
}