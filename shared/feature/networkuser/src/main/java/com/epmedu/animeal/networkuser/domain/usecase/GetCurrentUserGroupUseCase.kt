package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.users.domain.model.UserGroup

class GetCurrentUserGroupUseCase(private val networkRepository: NetworkRepository) {

    suspend operator fun invoke(shouldFetch: Boolean = false): ActionResult<UserGroup> {
        return networkRepository.getUserGroup(shouldFetch)
    }
}