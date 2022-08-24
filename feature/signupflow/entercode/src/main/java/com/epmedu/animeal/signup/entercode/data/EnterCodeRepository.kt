package com.epmedu.animeal.signup.entercode.data

import kotlinx.coroutines.flow.Flow

internal interface EnterCodeRepository {

    val phoneNumber: Flow<String>
}