package com.epmedu.animeal.profile.data.repository

import com.epmedu.animeal.profile.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(): Flow<Profile>

    suspend fun saveProfile(profile: Profile): Flow<Unit>

    suspend fun clearProfile()

    suspend fun isProfileSaved(): Boolean
}