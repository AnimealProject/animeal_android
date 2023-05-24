package com.epmedu.animeal.users.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class UserAttribute(
    @SerializedName("Name")
    val name: String,
    @SerializedName("Value")
    val value: String
)