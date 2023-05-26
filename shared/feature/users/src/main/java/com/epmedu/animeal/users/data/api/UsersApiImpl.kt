package com.epmedu.animeal.users.data.api

import com.amplifyframework.api.rest.RestOptions
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.users.data.model.User

internal class UsersApiImpl(
    private val animealApi: AnimealApi
) : UsersApi {

    override suspend fun getUserById(id: String): ApiResult<User> {
        val restOptions = RestOptions.builder()
            .addPath("/getUser")
            .addQueryParameters(mapOf("username" to id))
            .build()
        return animealApi.launchGetRequest(restOptions = restOptions, responseClass = User::class.java)
    }
}