package com.epmedu.animeal.auth

interface AuthAPI {

    var authenticationType: AuthenticationType

    suspend fun getCurrentUserId(): String
    suspend fun isSignedIn(): Boolean
    fun signUp(
        phone: String,
        password: String,
        handler: AuthRequestHandler,
    )

    fun signIn(
        phoneNumber: String,
        handler: AuthRequestHandler,
    )
    fun confirmSignIn(
        code: String,
        handler: AuthRequestHandler
    )
    fun confirmResendCode(
        code: String,
        handler: AuthRequestHandler
    )
    fun sendCode(
        phoneNumber: String,
        handler: AuthRequestHandler,
    )
    suspend fun signOut()
}
