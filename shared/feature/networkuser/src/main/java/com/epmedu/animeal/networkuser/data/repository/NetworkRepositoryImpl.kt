package com.epmedu.animeal.networkuser.data.repository

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.UserAttributesAPI
import com.epmedu.animeal.auth.constants.UserAttributesKey
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToProfileMapper
import com.epmedu.animeal.networkuser.data.mapper.ProfileToAuthUserAttributesMapper
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.profile.data.model.Profile
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI,
    private val userAttributesAPI: UserAttributesAPI,
    private val authUserAttributesToProfileMapper: AuthUserAttributesToProfileMapper,
    private val profileToAuthUserMapper: ProfileToAuthUserAttributesMapper,
) : NetworkRepository {

    override suspend fun isPhoneNumberVerified(): Boolean {
        val result = userAttributesAPI.fetchUserAttributes()
        return result is ApiResult.Success && result.data.isVerified()
    }

    private fun List<AuthUserAttribute>.isVerified() = find {
        it.key == AuthUserAttributeKey.custom(UserAttributesKey.phoneNumberVerifiedKey)
    }?.value.toBoolean()

    override suspend fun getNetworkProfile(): Profile? {
        return when (val result = userAttributesAPI.fetchUserAttributes()) {
            is ApiResult.Success -> authUserAttributesToProfileMapper.map(
                attributes = result.data,
                authenticationType = authAPI.authenticationType
            )
            is ApiResult.Failure -> null
        }
    }

    override suspend fun updateNetworkUserAttributes(profile: Profile): ActionResult {
        return userAttributesAPI.updateUserAttributes(profileToAuthUserMapper.map(profile)).toActionResult()
    }

    override suspend fun deleteNetworkUser(): ActionResult {
        return userAttributesAPI.deleteUser().toActionResult()
    }
}

private fun ApiResult<Unit>.toActionResult(): ActionResult {
    return when (this) {
        is ApiResult.Success -> ActionResult.Success
        is ApiResult.Failure -> ActionResult.Failure(error)
    }
}