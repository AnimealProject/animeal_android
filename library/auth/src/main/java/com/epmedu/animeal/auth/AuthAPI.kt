package com.epmedu.animeal.auth

import com.amplifyframework.auth.result.AuthSignInResult
import com.epmedu.animeal.common.data.wrapper.ApiResult

interface AuthAPI {

    var authenticationType: AuthenticationType

    suspend fun getCurrentUserId(): String
    suspend fun isSignedIn(): Boolean
    suspend fun signUp(
        phone: String,
        password: String
    ): ApiResult<Unit>

    suspend fun signIn(
        phoneNumber: String
    ): ApiResult<AuthSignInResult>

    suspend fun confirmSignIn(
        code: String
    ): ApiResult<AuthSignInResult>

    fun confirmResendCode(
        code: String,
        handler: AuthRequestHandler
    )

    suspend fun sendCode(
        phoneNumber: String,
    ): ApiResult<Any>

    suspend fun signOut() : ApiResult<Unit>
}
