package com.epmedu.animeal.login.phone.domain.model

internal data class EnterPhoneState(
    val phoneNumber: String = "",
    val isNextEnabled: Boolean = false
)