package com.epmedu.animeal.profile.domain.repository

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Profile>

    fun saveProfile(profile: Profile): Flow<Unit>

    suspend fun logOut(authRequestHandler: AuthRequestHandler)

    suspend fun clearProfile()

    suspend fun isProfileSaved(): Boolean
}