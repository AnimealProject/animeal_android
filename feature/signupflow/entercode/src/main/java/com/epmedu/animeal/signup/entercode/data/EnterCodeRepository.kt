package com.epmedu.animeal.signup.entercode.data

import kotlinx.coroutines.flow.Flow

interface EnterCodeRepository {
    val phoneNumber: Flow<String>

    fun sendCode(phoneNumber: String, onSuccess: () -> Unit, onError: () -> Unit)

    fun confirmCode(code: List<Int?>, onSuccess: () -> Unit, onError: () -> Unit)
}