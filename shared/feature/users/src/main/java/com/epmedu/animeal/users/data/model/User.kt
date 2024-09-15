package com.epmedu.animeal.users.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class User(
    @SerializedName("Username")
    val userId: String,
    @SerializedName(value = "UserAttributes", alternate = ["Attributes"])
    val userAttributes: List<UserAttribute>
)
