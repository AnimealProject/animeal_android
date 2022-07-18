package com.epmedu.animeal.login.profile.data.repository

import com.epmedu.animeal.login.profile.data.model.ProfileObj
import kotlinx.coroutines.flow.Flow

internal interface ProfileRepository {

    suspend fun getProfile(): Flow<ProfileObj>

    suspend fun saveProfile(profileObj: ProfileObj): Flow<Unit>
}