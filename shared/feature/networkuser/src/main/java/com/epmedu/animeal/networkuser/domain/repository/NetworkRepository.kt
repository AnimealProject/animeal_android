package com.epmedu.animeal.networkuser.domain.repository

import com.epmedu.animeal.profile.data.model.Profile

interface NetworkRepository {

    suspend fun isPhoneNumberVerified(): Boolean

    suspend fun getNetworkProfile(): Profile?

    suspend fun updateNetworkUserAttributes(
        profile: Profile,
        onSuccess: () -> Unit,
        onError: (exception: Throwable) -> Unit
    )

    suspend fun deleteNetworkUser(
        onSuccess: () -> Unit,
        onError: (exception: Throwable) -> Unit
    )
}