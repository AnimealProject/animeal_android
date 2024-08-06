package com.epmedu.animeal.users.data.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.users.data.api.UsersApi
import com.epmedu.animeal.users.data.mapper.toActionResult
import com.epmedu.animeal.users.data.mapper.toDomain
import com.epmedu.animeal.users.domain.UsersRepository
import com.epmedu.animeal.users.domain.model.User
import com.epmedu.animeal.users.domain.model.UserGroup
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class UsersRepositoryImpl(
    private val usersApi: UsersApi
) : UsersRepository {

    override fun getUserById(id: String): Flow<User?> {
        return flow {
            emit(
                usersApi.getUserById(id).data.toDomain(id)
            )
        }
    }

    override fun getUsersById(ids: Set<String>): Flow<List<User>> {
        return flow {
            coroutineScope {
                emit(
                    ids.map { id ->
                        async { id to usersApi.getUserById(id).data }
                    }
                        .awaitAll()
                        .map { pair ->
                            val userID = pair.first
                            val user = pair.second

                            user.toDomain(userID)
                        }
                )
            }
        }
    }

    override suspend fun getGroupsForUser(userId: String): ActionResult<List<UserGroup>> {
        return usersApi.getGroupsForUser(userId).toActionResult()
    }
}