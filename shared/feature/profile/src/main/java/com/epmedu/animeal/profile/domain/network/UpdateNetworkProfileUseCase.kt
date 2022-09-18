package com.epmedu.animeal.profile.domain.network

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.data.repository.NetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateNetworkProfileUseCase(private val repository: NetworkRepository) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

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
        coroutineScope.launch {
            repository.updateNetworkUserAttributes(profile, requestHandler)
        }
    }
}