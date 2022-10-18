package com.epmedu.animeal.signup.enterphone.data

internal interface EnterPhoneRepository {

    suspend fun savePhoneNumber(phoneNumber: String)

    suspend fun isPhoneNumberSaved(): Boolean
}