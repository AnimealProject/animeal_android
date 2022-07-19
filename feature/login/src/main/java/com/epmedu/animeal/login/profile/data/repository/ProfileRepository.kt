package com.epmedu.animeal.login.profile.data.repository

import com.epmedu.animeal.login.profile.data.model.Profile
import kotlinx.coroutines.flow.Flow

internal interface ProfileRepository {

    suspend fun getProfile(): Flow<Profile>

    suspend fun saveProfile(profile: Profile): Flow<Unit>
}