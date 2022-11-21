package com.epmedu.animeal.splash.domain

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.splash.data.SplashRepository

class FetchUserSessionUseCase(private val repository: SplashRepository) {
    operator fun invoke(
        onSuccess: (result: Any?) -> Unit,
        onError: (exception: Exception) -> Unit,
    ) {
        val handler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                onSuccess(result)
            }

            override fun onError(exception: Exception) {
                onError(exception)
            }
        }

        repository.fetchSession(handler)
    }
}