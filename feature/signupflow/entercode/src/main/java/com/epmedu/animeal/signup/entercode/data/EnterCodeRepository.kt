package com.epmedu.animeal.signup.entercode.data

import com.epmedu.animeal.auth.AuthRequestHandler
import kotlinx.coroutines.flow.Flow

interface EnterCodeRepository {
    val phoneNumberWithPrefix: Flow<String>

    suspend fun sendCode(requestHandler: AuthRequestHandler)

    fun confirmCode(code: List<Int?>, requestHandler: AuthRequestHandler)
}