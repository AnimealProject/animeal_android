package com.epmedu.animeal.users.data.mapper

import com.epmedu.animeal.users.data.model.User as DataUser
import com.epmedu.animeal.users.domain.model.User as DomainUser

internal fun DataUser.toDomain() = DomainUser(
    id = userId,
    name = userAttributes.find { it.name == NAME_ATTRIBUTE_KEY }?.value.orEmpty(),
    surname = userAttributes.find { it.name == SURNAME_ATTRIBUTE_KEY }?.value.orEmpty(),
    isTrusted = userAttributes.find { it.name == IS_TRUSTED_ATTRIBUTE_KEY }?.value.toBoolean()
)

private const val NAME_ATTRIBUTE_KEY = "name"
private const val SURNAME_ATTRIBUTE_KEY = "family_name"
private const val IS_TRUSTED_ATTRIBUTE_KEY = "custom:trusted"