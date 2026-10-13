package com.epmedu.animeal.profile.domain.repository

import com.epmedu.animeal.profile.domain.model.BasicProfile
import com.epmedu.animeal.profile.domain.model.Region
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<BasicProfile>

    fun getBasicProfile(): Flow<BasicProfile>

    fun saveProfile(profile: BasicProfile): Flow<Unit>

    suspend fun updatePhoneAndRegion(phone: String, region: Region)

    suspend fun clearProfile()

    suspend fun isProfileSaved(): Boolean
}