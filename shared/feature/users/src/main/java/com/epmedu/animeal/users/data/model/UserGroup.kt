package com.epmedu.animeal.users.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class UserGroup(
    @SerializedName("GroupName")
    val name: String,
    @SerializedName("Description")
    val description: String
)