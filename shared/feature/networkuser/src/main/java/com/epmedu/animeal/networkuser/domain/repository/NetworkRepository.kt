package com.epmedu.animeal.networkuser.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.profile.domain.model.BasicProfile
import com.epmedu.animeal.users.domain.model.UserGroup

interface NetworkRepository {

    suspend fun isPhoneNumberVerified(): ActionResult<Boolean>

    suspend fun isTrusted(): ActionResult<Boolean>

    suspend fun getUserId(): String

    suspend fun getUserGroup(shouldFetch: Boolean = false): ActionResult<UserGroup>

    suspend fun getNetworkProfile(): BasicProfile?

    suspend fun updateNetworkUserAttributes(profile: BasicProfile): ActionResult<Unit>

    suspend fun logOut(): ActionResult<Unit>

    suspend fun deleteNetworkUser(): ActionResult<Unit>
}