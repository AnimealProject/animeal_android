package com.epmedu.animeal.auth.constants

import com.amplifyframework.auth.AuthUserAttributeKey

object UserAttributesKey {
    const val phoneNumberVerifiedKey = "phone_number_verified"

    val nameKey: AuthUserAttributeKey = AuthUserAttributeKey.name()
    val surnameKey: AuthUserAttributeKey = AuthUserAttributeKey.familyName()
    val birthDateKey: AuthUserAttributeKey = AuthUserAttributeKey.birthdate()
    val phoneNumberKey: AuthUserAttributeKey = AuthUserAttributeKey.phoneNumber()
    val emailKey: AuthUserAttributeKey = AuthUserAttributeKey.email()
}