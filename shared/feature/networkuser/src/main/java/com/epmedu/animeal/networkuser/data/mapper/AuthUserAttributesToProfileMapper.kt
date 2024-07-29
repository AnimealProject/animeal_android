package com.epmedu.animeal.networkuser.data.mapper

import com.amplifyframework.auth.AuthUserAttribute
import com.epmedu.animeal.auth.constants.UserAttributesKey.emailKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.nameKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.phoneNumberKey
import com.epmedu.animeal.auth.constants.UserAttributesKey.surnameKey
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.domain.model.Region

class AuthUserAttributesToProfileMapper {

    fun map(attributes: List<AuthUserAttribute>): Profile {
        val phoneNumberWithPrefix = attributes.find { it.key == phoneNumberKey }?.value
        val phoneNumberRegion = phoneNumberWithPrefix?.let {
            Region.entries.find { phoneNumberWithPrefix.startsWith(it.phoneNumberCode) }
        } ?: Region.GE
        val phoneNumber =
            phoneNumberWithPrefix?.removePrefix(phoneNumberRegion.phoneNumberCode) ?: EMPTY_STRING
        return Profile(
            name = attributes.find { it.key == nameKey }?.value ?: "",
            surname = attributes.find { it.key == surnameKey }?.value ?: "",
            phoneNumberRegion = phoneNumberRegion,
            phoneNumber = phoneNumber,
            email = attributes.find { it.key == emailKey }?.value ?: "",
        )
    }
}