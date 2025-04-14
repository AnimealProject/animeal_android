package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.profile.domain.model.BasicProfile

class UpdateNetworkProfileUseCase(private val repository: NetworkRepository) {

    suspend operator fun invoke(profile: BasicProfile) = repository.updateNetworkUserAttributes(profile)
}