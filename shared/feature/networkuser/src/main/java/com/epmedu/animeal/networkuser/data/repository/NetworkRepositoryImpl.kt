package com.epmedu.animeal.networkuser.data.repository

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.auth.UserAttributesAPI
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToIsPhoneVerifiedMapper
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToProfileMapper
import com.epmedu.animeal.networkuser.data.mapper.ProfileToAuthUserAttributesMapper
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.profile.data.model.Profile
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI,
    private val userAttributesAPI: UserAttributesAPI,
    private val authUserAttributesToIsPhoneVerifiedMapper: AuthUserAttributesToIsPhoneVerifiedMapper,
    private val authUserAttributesToProfileMapper: AuthUserAttributesToProfileMapper,
    private val profileToAuthUserMapper: ProfileToAuthUserAttributesMapper,
) : NetworkRepository {

    override suspend fun isPhoneNumberVerified(): Boolean {
        val result = userAttributesAPI.fetchUserAttributes()
        return result is ApiResult.Success && authUserAttributesToIsPhoneVerifiedMapper.map(result.data)
    }

    override suspend fun getNetworkProfile(): Profile? {
        return when (val result = userAttributesAPI.fetchUserAttributes()) {
            is ApiResult.Success -> authUserAttributesToProfileMapper.map(
                attributes = result.data,
                authenticationType = authAPI.authenticationType
            )
            is ApiResult.Failure -> null
        }
    }

    override suspend fun updateNetworkUserAttributes(profile: Profile, authRequestHandler: AuthRequestHandler) {
        userAttributesAPI.updateUserAttributes(profileToAuthUserMapper.map(profile), authRequestHandler)
    }

    override suspend fun deleteNetworkUser(authRequestHandler: AuthRequestHandler) {
        userAttributesAPI.deleteUser(authRequestHandler)
    }
}