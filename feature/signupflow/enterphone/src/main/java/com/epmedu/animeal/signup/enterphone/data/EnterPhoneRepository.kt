package com.epmedu.animeal.signup.enterphone.data

interface EnterPhoneRepository {

    suspend fun savePhoneNumber(phoneNumber: String)

    suspend fun isPhoneNumberSaved(): Boolean

    fun signUpAndSignIn(
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    )

    fun signIn(
        phoneNumber: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    )
}