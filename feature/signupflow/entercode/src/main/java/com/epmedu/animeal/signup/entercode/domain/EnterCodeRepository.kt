package com.epmedu.animeal.signup.entercode.domain

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import kotlinx.coroutines.flow.Flow

interface EnterCodeRepository {
    val phoneNumberWithPrefix: Flow<String>

    suspend fun sendCode(): ActionResult<Any>
    suspend fun confirmSignIn(code: List<Int?>): ActionResult<Unit>
    suspend fun confirmResendCode(code: List<Int?>): ActionResult<Unit>
}