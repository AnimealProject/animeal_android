package com.epmedu.animeal.splash.domain

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.splash.data.SplashRepository

class FetchUserSessionUseCase(private val repository: SplashRepository) {
    operator fun invoke(
        authRequestHandler: AuthRequestHandler
    ) {
        repository.fetchSession(authRequestHandler)
    }
}