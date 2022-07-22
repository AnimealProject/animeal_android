package com.epmedu.animeal.login.phone.data

internal interface EnterPhoneRepository {

    suspend fun savePhoneNumberAndSendCode(phoneNumber: String)
}