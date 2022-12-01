package com.epmedu.animeal.splash.data

import com.epmedu.animeal.auth.AuthRequestHandler

interface SplashRepository {
    fun fetchSession(authRequestHandler: AuthRequestHandler)
}