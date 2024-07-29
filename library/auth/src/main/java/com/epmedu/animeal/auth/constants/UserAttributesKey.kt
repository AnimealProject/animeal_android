package com.epmedu.animeal.auth.constants

import com.amplifyframework.auth.AuthUserAttributeKey

object UserAttributesKey {
    const val PHONE_NUMBER_VERIFIED_KEY = "phone_number_verified"

    val nameKey: AuthUserAttributeKey = AuthUserAttributeKey.name()
    val surnameKey: AuthUserAttributeKey = AuthUserAttributeKey.familyName()
    val phoneNumberKey: AuthUserAttributeKey = AuthUserAttributeKey.phoneNumber()
    val emailKey: AuthUserAttributeKey = AuthUserAttributeKey.email()
}