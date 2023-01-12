package com.epmedu.animeal.profile.data.repository

import com.epmedu.animeal.navigation.route.AuthenticationType

interface AuthenticationTypeRepository {

    suspend fun getAuthenticationType(): AuthenticationType

    suspend fun updateAuthenticationType(authenticationType: AuthenticationType)
}