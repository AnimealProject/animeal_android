package com.epmedu.animeal.profile.data.repository

import com.epmedu.animeal.profile.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Profile>

    fun saveProfile(profile: Profile): Flow<Unit>

    suspend fun logOut(onSuccess: () -> Unit, onError: () -> Unit)

    suspend fun clearProfile()

    suspend fun isProfileSaved(): Boolean
}