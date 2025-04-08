package com.epmedu.animeal.profile.domain.model

import com.epmedu.animeal.common.constants.DefaultConstants

sealed interface Profile

data class BasicProfile(
    val name: String = DefaultConstants.EMPTY_STRING,
    val surname: String = DefaultConstants.EMPTY_STRING,
    val phoneNumberRegion: Region = Region.GE,
    val phoneNumber: String = DefaultConstants.EMPTY_STRING,
    val email: String = DefaultConstants.EMPTY_STRING,
) : Profile {
    fun isFilled() = name.isNotBlank() && surname.isNotBlank() && email.isNotBlank()
}

data object GuestProfile : Profile
