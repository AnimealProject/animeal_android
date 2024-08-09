package com.epmedu.animeal.users.data.api

import com.amplifyframework.api.rest.RestOptions
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.users.data.model.User
import com.epmedu.animeal.users.data.model.UserGroups
import com.epmedu.animeal.users.data.model.Users
import com.epmedu.animeal.users.domain.model.UserGroup

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

    override suspend fun getGroupsForUser(userId: String): ApiResult<UserGroups> {
        val restOptions = RestOptions.builder()
            .addPath("/listGroupsForUser")
            .addQueryParameters(mapOf("username" to userId))
            .build()
        return animealApi.launchGetRequest(
            restOptions = restOptions,
            responseClass = UserGroups::class.java
        )
    }

    override suspend fun getUsersInGroup(group: UserGroup): ApiResult<Users> {
        val restOptions = RestOptions.builder()
            .addPath("/listUsersInGroup")
            .addQueryParameters(mapOf("groupname" to group.name))
            .build()
        return animealApi.launchGetRequest(
            restOptions = restOptions,
            responseClass = Users::class.java
        )
    }
}