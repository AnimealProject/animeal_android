package com.epmedu.animeal.signup.entercode.domain

import com.amplifyframework.auth.result.AuthSignInResult
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow

interface EnterCodeRepository {
    val phoneNumberWithPrefix: Flow<String>

    suspend fun sendCode(requestHandler: AuthRequestHandler)

    suspend fun confirmSignIn(code: List<Int?>): ApiResult<AuthSignInResult>

    fun confirmResendCode(code: List<Int?>, requestHandler: AuthRequestHandler)
}