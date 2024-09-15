package com.epmedu.animeal.users.domain

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.users.domain.model.User
import com.epmedu.animeal.users.domain.model.UserGroup
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getUserById(id: String): Flow<User?>

    fun getUsersById(ids: Set<String>): Flow<List<User>>

    suspend fun getGroupsForUser(userId: String): ActionResult<List<UserGroup>>

    fun getUsersInGroup(group: UserGroup): Flow<List<User>>
}