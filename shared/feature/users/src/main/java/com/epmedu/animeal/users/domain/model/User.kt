package com.epmedu.animeal.users.domain.model

import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING

data class User(
    val id: String,
    val name: String,
    val surname: String = EMPTY_STRING,
    val isTrusted: Boolean = false
)
