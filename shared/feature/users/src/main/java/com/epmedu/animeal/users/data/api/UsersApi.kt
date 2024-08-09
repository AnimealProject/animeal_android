package com.epmedu.animeal.users.data.api

import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.users.data.model.User
import com.epmedu.animeal.users.data.model.UserGroups
import com.epmedu.animeal.users.data.model.Users
import com.epmedu.animeal.users.domain.model.UserGroup

internal interface UsersApi {

    suspend fun getUserById(id: String): ApiResult<User>

    suspend fun getGroupsForUser(userId: String): ApiResult<UserGroups>

    suspend fun getUsersInGroup(group: UserGroup): ApiResult<Users>
}