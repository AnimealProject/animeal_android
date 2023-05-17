package com.epmedu.animeal.users.domain

import com.epmedu.animeal.users.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getUsersById(ids: Set<String>): Flow<List<User>>
}