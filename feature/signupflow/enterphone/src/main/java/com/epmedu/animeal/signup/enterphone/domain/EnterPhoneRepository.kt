package com.epmedu.animeal.signup.enterphone.domain

import com.amplifyframework.auth.result.AuthSignInResult
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.profile.domain.model.Region

interface EnterPhoneRepository {

    suspend fun savePhoneNumberAndRegion(
        region: Region,
        phoneNumber: String
    )

    suspend fun signUp(
        phone: String,
        password: String
    ): ActionResult<Unit>

    suspend fun signIn(
        phoneNumber: String
    ): ActionResult<AuthSignInResult>
}