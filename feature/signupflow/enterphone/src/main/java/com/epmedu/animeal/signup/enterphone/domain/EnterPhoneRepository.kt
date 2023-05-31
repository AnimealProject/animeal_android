package com.epmedu.animeal.signup.enterphone.domain

import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.profile.domain.model.Region

interface EnterPhoneRepository {

    suspend fun savePhoneNumberAndRegion(
        region: Region,
        phoneNumber: String
    )

    suspend fun signUp(
        phone: String,
        password: String
    ): ApiResult<Unit>

    suspend fun signIn(
        phoneNumber: String
    ): ApiResult<Unit>
}