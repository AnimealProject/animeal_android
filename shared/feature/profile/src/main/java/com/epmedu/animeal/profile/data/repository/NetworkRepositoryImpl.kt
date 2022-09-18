package com.epmedu.animeal.profile.data.repository

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.data.mapper.ProfileToAuthUserAttributesMapper
import com.epmedu.animeal.profile.data.model.Profile
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val profileToAuthUserMapper: ProfileToAuthUserAttributesMapper,
    private val authAPI: AuthAPI,
) : NetworkRepository {

    override suspend fun fetchNetworkUserAttributes(authRequestHandler: AuthRequestHandler) {
        authAPI.fetchUserAttributes(authRequestHandler)
    }

    override suspend fun updateNetworkUserAttributes(profile: Profile, authRequestHandler: AuthRequestHandler) {
        authAPI.updateUserAttributes(profileToAuthUserMapper.map(profile), authRequestHandler)
    }

    override suspend fun deleteNetworkUser(authRequestHandler: AuthRequestHandler) {
        authAPI.deleteUser(authRequestHandler)
    }
}