package com.epmedu.animeal.networkuser.data.repository

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthenticationType
import javax.inject.Inject

class AuthenticationTypeRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthenticationTypeRepository {

    override suspend fun getAuthenticationType(): AuthenticationType = authAPI.authenticationType

    override suspend fun setAuthenticationTypeAsMobile() {
        authAPI.setMobileAuthenticationType()
    }

    override suspend fun setAuthenticationTypeAsFacebook() {
        authAPI.setFacebookAuthenticationType()
    }
}