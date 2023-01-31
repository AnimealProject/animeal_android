package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.profile.data.model.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateNetworkProfileUseCase(private val repository: NetworkRepository) {

    suspend operator fun invoke(
        profile: Profile,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit
    ) {
        val requestHandler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                onSuccess()
            }

            override fun onError(exception: Exception) {
                onError(exception)
            }
        }
        withContext(Dispatchers.IO) {
            repository.updateNetworkUserAttributes(profile, requestHandler)
        }
    }
}