package com.epmedu.animeal.networkuser.domain.repository

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.data.model.Profile

interface NetworkRepository {

    suspend fun fetchNetworkUserAttributes(authRequestHandler: AuthRequestHandler)

    suspend fun updateNetworkUserAttributes(profile: Profile, authRequestHandler: AuthRequestHandler)

    suspend fun deleteNetworkUser(authRequestHandler: AuthRequestHandler)
}