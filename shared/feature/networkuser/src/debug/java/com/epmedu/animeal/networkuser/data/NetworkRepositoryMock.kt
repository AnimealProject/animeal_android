package com.epmedu.animeal.networkuser.data

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.users.domain.model.UserGroup

class NetworkRepositoryMock : NetworkRepository {
    override suspend fun isPhoneNumberVerified(): ActionResult<Boolean> {
        return ActionResult.Success(true)
    }

    override suspend fun isTrusted(): ActionResult<Boolean> {
        return ActionResult.Success(true)
    }

    override suspend fun getUserId(): String {
        return "mocked_user_id"
    }

    override suspend fun getUserGroup(shouldFetch: Boolean): ActionResult<UserGroup> {
        return ActionResult.Success(UserGroup.Administrator)
    }

    override suspend fun getNetworkProfile(): Profile? {
        return null
    }

    override suspend fun updateNetworkUserAttributes(profile: Profile): ActionResult<Unit> {
        return ActionResult.Success(Unit)
    }

    override suspend fun logOut(): ActionResult<Unit> {
        return ActionResult.Success(Unit)
    }

    override suspend fun deleteNetworkUser(): ActionResult<Unit> {
        return ActionResult.Success(Unit)
    }
}