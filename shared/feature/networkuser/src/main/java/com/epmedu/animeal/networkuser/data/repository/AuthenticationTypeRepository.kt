package com.epmedu.animeal.networkuser.data.repository

import com.epmedu.animeal.auth.AuthenticationType

interface AuthenticationTypeRepository {

    suspend fun getAuthenticationType(): AuthenticationType

    suspend fun setAuthenticationTypeAsMobile()

    suspend fun setAuthenticationTypeAsFacebook()
}