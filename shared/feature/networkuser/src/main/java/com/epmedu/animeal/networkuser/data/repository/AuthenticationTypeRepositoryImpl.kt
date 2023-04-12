package com.epmedu.animeal.networkuser.data.repository

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.networkuser.domain.repository.AuthenticationTypeRepository
import javax.inject.Inject

class AuthenticationTypeRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthenticationTypeRepository {

    override suspend fun getAuthenticationType(): AuthenticationType = authAPI.authenticationType

    override fun setAuthenticationTypeAsMobile() {
        authAPI.authenticationType = AuthenticationType.Mobile
    }

    override fun setAuthenticationTypeAsFacebook(isPhoneNumberVerified: Boolean) {
        authAPI.authenticationType = AuthenticationType.Facebook(isPhoneNumberVerified)
    }
}