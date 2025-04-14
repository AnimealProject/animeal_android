package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.profile.domain.model.BasicProfile

class GetNetworkProfileUseCase(
    private val repository: NetworkRepository,
    private val getCurrentUserGroupUseCase: GetCurrentUserGroupUseCase
) {

    suspend operator fun invoke(): BasicProfile? {
        // fetch current user group before navigating further
        getCurrentUserGroupUseCase(shouldFetch = true)

        return repository.getNetworkProfile()
    }
}