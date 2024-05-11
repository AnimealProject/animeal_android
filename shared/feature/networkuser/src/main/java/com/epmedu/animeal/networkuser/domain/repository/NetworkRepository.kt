package com.epmedu.animeal.networkuser.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.users.domain.model.UserGroup

interface NetworkRepository {

    suspend fun isPhoneNumberVerified(): ActionResult<Boolean>

    suspend fun getUserId(): String

    suspend fun getUserGroup(shouldFetch: Boolean = false): ActionResult<UserGroup>

    suspend fun getNetworkProfile(): Profile?

    suspend fun updateNetworkUserAttributes(profile: Profile): ActionResult<Unit>

    suspend fun logOut(): ActionResult<Unit>

    suspend fun deleteNetworkUser(): ActionResult<Unit>
}