package com.epmedu.animeal.profile.data.model

import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.profile.domain.model.Region

// TODO: Move to domain
data class Profile(
    val name: String = EMPTY_STRING,
    val surname: String = EMPTY_STRING,
    val birthDate: String = EMPTY_STRING,
    val phoneNumberRegion: Region = Region.GE,
    val phoneNumber: String = EMPTY_STRING,
    val email: String = EMPTY_STRING
) {
    fun isFilled() =
        name.isNotBlank() && surname.isNotBlank() && birthDate.isNotBlank() && email.isNotBlank()
}