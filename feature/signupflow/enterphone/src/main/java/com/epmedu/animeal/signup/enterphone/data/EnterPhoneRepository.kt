package com.epmedu.animeal.signup.enterphone.data

import com.epmedu.animeal.auth.AuthRequestHandler

interface EnterPhoneRepository {

    suspend fun savePhoneNumberAndPrefix(
        prefix: String,
        phoneNumber: String
    )

    fun signUp(
        phone: String,
        password: String,
        requestHandler: AuthRequestHandler,
    )

    fun signIn(
        phoneNumber: String,
        requestHandler: AuthRequestHandler,
    )
}