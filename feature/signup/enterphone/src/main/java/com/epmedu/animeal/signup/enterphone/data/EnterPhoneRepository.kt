package com.epmedu.animeal.signup.enterphone.data

internal interface EnterPhoneRepository {

    suspend fun savePhoneNumberAndSendCode(phoneNumber: String)
}