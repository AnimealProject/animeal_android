package com.epmedu.animeal.splash.data

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI,
) : SplashRepository {
    override fun fetchSession(authRequestHandler: AuthRequestHandler) {
        authAPI.fetchSession(authRequestHandler)
    }
}