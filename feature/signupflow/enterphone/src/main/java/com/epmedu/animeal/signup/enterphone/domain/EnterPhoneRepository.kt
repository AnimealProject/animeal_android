package com.epmedu.animeal.signup.enterphone.domain

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.domain.model.Region

interface EnterPhoneRepository {

    suspend fun savePhoneNumberAndRegion(
        region: Region,
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