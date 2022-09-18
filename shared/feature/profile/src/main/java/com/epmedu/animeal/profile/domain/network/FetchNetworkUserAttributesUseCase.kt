package com.epmedu.animeal.profile.domain.network

import com.amplifyframework.auth.AuthUserAttribute
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.data.repository.NetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.function.Consumer

class FetchNetworkUserAttributesUseCase(private val repository: NetworkRepository) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke(
        onSuccess: Consumer<List<AuthUserAttribute>>,
        onError: (exception: Exception) -> Unit
    ) {
        val requestHandler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                onSuccess.accept(result as List<AuthUserAttribute>)
            }

            override fun onError(exception: Exception) {
                onError(exception)
            }
        }
        coroutineScope.launch {
            repository.fetchNetworkUserAttributes(requestHandler)
        }
    }
}