package com.epmedu.animeal.networkuser.domain.repository

import com.epmedu.animeal.auth.AuthenticationType

interface AuthenticationTypeRepository {

    suspend fun getAuthenticationType(): AuthenticationType

    fun setAuthenticationTypeAsMobile()

    fun setAuthenticationTypeAsFacebook(isPhoneNumberVerified: Boolean)
}