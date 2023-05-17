package com.epmedu.animeal.users.data.api

import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.users.data.model.User

internal interface UsersApi {

    suspend fun getUserById(id: String): ApiResult<User>
}