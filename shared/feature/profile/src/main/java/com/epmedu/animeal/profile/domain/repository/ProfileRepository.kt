package com.epmedu.animeal.profile.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.profile.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Profile>

    fun saveProfile(profile: Profile): Flow<Unit>

    suspend fun logOut(): ActionResult<Unit>

    suspend fun clearProfile()

    suspend fun isProfileSaved(): Boolean
}