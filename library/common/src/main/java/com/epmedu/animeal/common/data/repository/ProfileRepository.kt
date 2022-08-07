package com.epmedu.animeal.common.data.repository

import com.epmedu.animeal.common.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(): Flow<Profile>

    suspend fun saveProfile(profile: Profile): Flow<Unit>
}