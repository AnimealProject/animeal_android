package com.epmedu.animeal.networkuser.domain.usecase

import com.amplifyframework.auth.AuthUserAttribute
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.function.Consumer

class FetchNetworkUserAttributesUseCase(private val repository: NetworkRepository) {

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
        withContext(Dispatchers.IO) {
            repository.fetchNetworkUserAttributes(requestHandler)
        }
    }
}