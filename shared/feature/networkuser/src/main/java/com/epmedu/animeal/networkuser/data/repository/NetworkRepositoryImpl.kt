package com.epmedu.animeal.networkuser.data.repository

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.auth.UserAttributesAPI
import com.epmedu.animeal.networkuser.data.mapper.ProfileToAuthUserAttributesMapper
import com.epmedu.animeal.profile.data.model.Profile
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val profileToAuthUserMapper: ProfileToAuthUserAttributesMapper,
    private val userAttributesAPI: UserAttributesAPI,
) : NetworkRepository {

    override suspend fun fetchNetworkUserAttributes(authRequestHandler: AuthRequestHandler) {
        userAttributesAPI.fetchUserAttributes(authRequestHandler)
    }

    override suspend fun updateNetworkUserAttributes(profile: Profile, authRequestHandler: AuthRequestHandler) {
        userAttributesAPI.updateUserAttributes(profileToAuthUserMapper.map(profile), authRequestHandler)
    }

    override suspend fun deleteNetworkUser(authRequestHandler: AuthRequestHandler) {
        userAttributesAPI.deleteUser(authRequestHandler)
    }
}