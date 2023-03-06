package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.profile.data.model.Profile

class UpdateNetworkProfileUseCase(private val repository: NetworkRepository) {

    suspend operator fun invoke(profile: Profile) = repository.updateNetworkUserAttributes(profile)
}