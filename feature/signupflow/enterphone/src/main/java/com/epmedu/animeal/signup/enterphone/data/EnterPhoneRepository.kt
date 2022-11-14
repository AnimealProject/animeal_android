package com.epmedu.animeal.signup.enterphone.data

import com.epmedu.animeal.auth.AuthRequestHandler

interface EnterPhoneRepository {

    suspend fun savePhoneNumber(phoneNumber: String)

    suspend fun isPhoneNumberSaved(): Boolean

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