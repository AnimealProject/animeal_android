package com.epmedu.animeal.networkuser.domain

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.networkuser.data.repository.NetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteNetworkUserUseCase(private val repository: NetworkRepository) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke(
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
            repository.deleteNetworkUser(requestHandler)
        }
    }
}