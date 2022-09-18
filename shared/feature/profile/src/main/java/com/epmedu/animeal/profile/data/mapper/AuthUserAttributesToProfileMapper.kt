package com.epmedu.animeal.profile.data.mapper

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.epmedu.animeal.foundation.common.validation.Constants.PHONE_NUMBER_LENGTH
import com.epmedu.animeal.profile.data.model.Profile

class AuthUserAttributesToProfileMapper {

    fun map(attributes: List<AuthUserAttribute>): Profile {
        return Profile(
            name = attributes.find { it.key == AuthUserAttributeKey.givenName() }?.value ?: "",
            surname = attributes.find { it.key == AuthUserAttributeKey.familyName() }?.value ?: "",
            birthDate = attributes.find { it.key == AuthUserAttributeKey.birthdate() }?.value ?: "",
            phoneNumber = attributes.find { it.key == AuthUserAttributeKey.phoneNumber() }?.value
                ?.takeLast(PHONE_NUMBER_LENGTH) ?: "",
            email = attributes.find { it.key == AuthUserAttributeKey.email() }?.value ?: ""
        )
    }
}