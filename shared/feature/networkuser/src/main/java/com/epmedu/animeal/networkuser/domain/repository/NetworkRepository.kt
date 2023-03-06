package com.epmedu.animeal.networkuser.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.profile.data.model.Profile

interface NetworkRepository {

    suspend fun isPhoneNumberVerified(): Boolean

    suspend fun getNetworkProfile(): Profile?

    suspend fun updateNetworkUserAttributes(profile: Profile): ActionResult

    suspend fun deleteNetworkUser(): ActionResult
}