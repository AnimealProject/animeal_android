package com.epmedu.animeal.users.data.model

import com.google.errorprone.annotations.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class Users(
    @SerializedName("Users")
    val users: List<User>
)
