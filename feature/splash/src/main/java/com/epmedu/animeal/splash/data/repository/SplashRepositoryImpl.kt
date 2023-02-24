package com.epmedu.animeal.splash.data.repository

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.splash.domain.repository.SplashRepository
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI
) : SplashRepository {

    override suspend fun isSignedIn(): Boolean {
        return authAPI.isSignedIn()
    }
}