package com.epmedu.animeal.signup.enterphone.domain

import com.epmedu.animeal.common.domain.wrapper.ActionResult

interface EnterPhoneRepository {

    suspend fun signUp(
        phone: String,
        password: String
    ): ActionResult<Unit>

    suspend fun signIn(
        phoneNumber: String
    ): ActionResult<Unit>
}