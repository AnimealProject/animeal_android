package com.epmedu.animeal.auth

import com.amplifyframework.auth.AuthUserAttribute
import com.epmedu.animeal.common.data.wrapper.ApiResult

interface UserAttributesAPI {

    suspend fun fetchUserAttributes(): ApiResult<List<AuthUserAttribute>>

    suspend fun updateUserAttributes(
        userAttributes: List<AuthUserAttribute>,
    ): ApiResult<Unit>

    suspend fun deleteUser(): ApiResult<Unit>
}