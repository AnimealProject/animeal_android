package com.epmedu.animeal.login.profile.data.repository

import com.epmedu.animeal.login.profile.data.model.ProfileObj
import com.epmedu.animeal.login.profile.data.storage.ProfileStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class ProfileRepositoryImpl @Inject constructor(
    private val profileStorage: ProfileStorage
) : ProfileRepository {

    override suspend fun getProfile(): Flow<ProfileObj> {
        return profileStorage.profileFlow
    }

    override suspend fun saveProfile(profile: ProfileObj): Flow<Unit> {
        return flowOf(profileStorage.saveProfile(profile))
    }
}