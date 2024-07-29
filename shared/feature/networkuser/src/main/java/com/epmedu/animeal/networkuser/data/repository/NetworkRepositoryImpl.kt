package com.epmedu.animeal.networkuser.data.repository

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.exceptions.NotAuthorizedException
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.UserAttributesAPI
import com.epmedu.animeal.auth.constants.UserAttributesKey
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToProfileMapper
import com.epmedu.animeal.networkuser.data.mapper.ProfileToAuthUserAttributesMapper
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.users.domain.UsersRepository
import com.epmedu.animeal.users.domain.model.UserGroup
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI,
    private val userAttributesAPI: UserAttributesAPI,
    private val usersRepository: UsersRepository,
    private val authUserAttributesToProfileMapper: AuthUserAttributesToProfileMapper,
    private val profileToAuthUserMapper: ProfileToAuthUserAttributesMapper,
) : NetworkRepository {

    private var currentUserGroupResult: ActionResult<UserGroup>? = null

    override suspend fun isPhoneNumberVerified(): ActionResult<Boolean> {
        val result = userAttributesAPI.fetchUserAttributes()
        /** NotAuthorizedException is the backend refresh-token expiration exception */
        if (result is ApiResult.Failure && result.error is NotAuthorizedException) {
            return ActionResult.Failure(result.error)
        }
        return ActionResult.Success(result is ApiResult.Success && result.data.isVerified())
    }

    override suspend fun getUserId(): String = authAPI.getCurrentUserId()

    private fun List<AuthUserAttribute>.isVerified() = find {
        it.key == AuthUserAttributeKey.custom(UserAttributesKey.PHONE_NUMBER_VERIFIED_KEY)
    }?.value.toBoolean()

    override suspend fun getUserGroup(shouldFetch: Boolean): ActionResult<UserGroup> {
        val cachedResult = currentUserGroupResult
        return when {
            shouldFetch || cachedResult == null || cachedResult is ActionResult.Failure -> {
                fetchUserGroup()
            }
            else -> {
                cachedResult
            }
        }
    }

    private suspend fun fetchUserGroup(): ActionResult<UserGroup> {
        return when (val result = usersRepository.getGroupsForUser(authAPI.getCurrentUserId())) {
            is ActionResult.Success -> {
                result.result.maxByOrNull { it.ordinal }?.let { group ->
                    ActionResult.Success(group)
                } ?: ActionResult.Failure(IllegalArgumentException("User is not added to any group"))
            }
            is ActionResult.Failure -> {
                ActionResult.Failure(result.error)
            }
        }.also { result ->
            currentUserGroupResult = result
        }
    }

    override suspend fun getNetworkProfile(): Profile? {
        return when (val result = userAttributesAPI.fetchUserAttributes()) {
            is ApiResult.Success -> authUserAttributesToProfileMapper.map(attributes = result.data)
            is ApiResult.Failure -> null
        }
    }

    override suspend fun updateNetworkUserAttributes(profile: Profile): ActionResult<Unit> {
        return userAttributesAPI.updateUserAttributes(profileToAuthUserMapper.map(profile)).toActionResult()
    }

    override suspend fun logOut(): ActionResult<Unit> {
        return when (val result = authAPI.signOut()) {
            is ApiResult.Success -> {
                currentUserGroupResult = null
                ActionResult.Success(result.data)
            }
            is ApiResult.Failure -> {
                ActionResult.Failure(result.error)
            }
        }
    }

    override suspend fun deleteNetworkUser(): ActionResult<Unit> {
        return userAttributesAPI.deleteUser().toActionResult()
    }
}

private fun ApiResult<Unit>.toActionResult(): ActionResult<Unit> {
    return when (this) {
        is ApiResult.Success -> ActionResult.Success(Unit)
        is ApiResult.Failure -> ActionResult.Failure(error)
    }
}