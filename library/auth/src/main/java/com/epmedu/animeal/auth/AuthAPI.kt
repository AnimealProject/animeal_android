package com.epmedu.animeal.auth

import com.epmedu.animeal.common.data.wrapper.ApiResult

interface AuthAPI {

    var authenticationType: AuthenticationType

    suspend fun getCurrentUserId(): String
    suspend fun isSignedIn(): Boolean
    suspend fun signUp(phone: String, password: String): ApiResult<Unit>
    suspend fun signIn(phoneNumber: String): ApiResult<Unit>
    suspend fun confirmSignIn(code: String): ApiResult<Unit>
    suspend fun confirmResendCode(code: String): ApiResult<Unit>
    suspend fun sendCode(phoneNumber: String): ApiResult<Unit>
    suspend fun signOut(): ApiResult<Unit>
}
