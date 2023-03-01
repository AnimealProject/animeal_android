package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.profile.data.model.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateNetworkProfileUseCase(private val repository: NetworkRepository) {

    suspend operator fun invoke(
        profile: Profile,
        onSuccess: () -> Unit,
        onError: (exception: Throwable) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            repository.updateNetworkUserAttributes(profile, onSuccess, onError)
        }
    }
}