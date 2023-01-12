package com.epmedu.animeal.profile.data.repository

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.navigation.route.AuthenticationType
import javax.inject.Inject

class AuthenticationTypeRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthenticationTypeRepository {

    override suspend fun getAuthenticationType(): AuthenticationType = authAPI.authenticationType

    override suspend fun updateAuthenticationType(authenticationType: AuthenticationType) {
        authAPI.authenticationType = authenticationType
    }
}