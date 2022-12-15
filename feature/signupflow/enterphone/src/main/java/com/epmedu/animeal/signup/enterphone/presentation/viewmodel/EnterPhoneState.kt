package com.epmedu.animeal.signup.enterphone.presentation.viewmodel

import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.common.constants.DefaultConstants.PHONE_NUMBER_PREFIX

internal data class EnterPhoneState(
    val prefix: String = PHONE_NUMBER_PREFIX,
    val phoneNumber: String = EMPTY_STRING,
    val isNextEnabled: Boolean = false,
    val isError: Boolean = false
)