package com.epmedu.animeal.auth

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
    ): ApiResult<Unit>
    fun confirmSignIn(
        code: String,
        handler: AuthRequestHandler
    )
    fun confirmResendCode(
        code: String,
        handler: AuthRequestHandler
    )
    suspend fun sendCode(
        phoneNumber: String,
        handler: AuthRequestHandler,
    )
    suspend fun signOut()
}
