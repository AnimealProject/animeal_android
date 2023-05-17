package com.epmedu.animeal.users.data.repository

import com.epmedu.animeal.users.data.api.UsersApi
import com.epmedu.animeal.users.data.toDomain
import com.epmedu.animeal.users.domain.UsersRepository
import com.epmedu.animeal.users.domain.model.User
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class UsersRepositoryImpl(
    private val usersApi: UsersApi
) : UsersRepository {

    override fun getUsersById(ids: Set<String>): Flow<List<User>> {
        return flow {
            coroutineScope {
                emit(
                    ids.map { id ->
                        async { usersApi.getUserById(id).data }
                    }
                        .awaitAll()
                        .mapNotNull { dataUser ->
                            dataUser?.toDomain()
                        }
                )
            }
        }
    }
}