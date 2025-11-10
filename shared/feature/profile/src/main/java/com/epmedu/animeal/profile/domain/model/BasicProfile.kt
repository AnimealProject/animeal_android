package com.epmedu.animeal.profile.domain.model

import com.epmedu.animeal.common.constants.DefaultConstants
import com.google.gson.annotations.SerializedName

data class BasicProfile(
    @SerializedName("name")
    val name: String = DefaultConstants.EMPTY_STRING,
    @SerializedName("surname")
    val surname: String = DefaultConstants.EMPTY_STRING,
    @SerializedName("phoneNumberRegion")
    val phoneNumberRegion: Region = Region.GE,
    @SerializedName("phoneNumber")
    val phoneNumber: String = DefaultConstants.EMPTY_STRING,
    @SerializedName("email")
    val email: String = DefaultConstants.EMPTY_STRING,
) {
    fun isFilled() = name.isNotBlank() && surname.isNotBlank() && email.isNotBlank()
}