package com.epmedu.animeal.signup.enterphone.presentation.viewmodel

import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.profile.domain.model.getFormat

internal data class EnterPhoneState(
    val region: Region = Region.GE,
    val phoneNumber: String = EMPTY_STRING,
    val isNextEnabled: Boolean = false,
    val isError: Boolean = false,
    val isDebug: Boolean,
) {
    val prefix: String
        get() {
            return region.phoneNumberCode
        }
    val format: String
        get() {
            return region.getFormat()
        }
    val numberLength: Int
        get() {
            return region.phoneNumberDigitsCount.last()
        }
}