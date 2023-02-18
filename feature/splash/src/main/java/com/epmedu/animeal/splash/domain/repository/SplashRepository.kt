package com.epmedu.animeal.splash.domain.repository

interface SplashRepository {

    suspend fun isSignedIn(): Boolean
}