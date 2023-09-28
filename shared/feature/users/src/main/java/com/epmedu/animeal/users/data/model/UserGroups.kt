package com.epmedu.animeal.users.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class UserGroups(
    @SerializedName("Groups")
    val userGroups: List<UserGroup>
)
