package com.epmedu.animeal.login.code.data

import kotlinx.coroutines.flow.Flow

internal interface EnterCodeRepository {

    val phoneNumber: Flow<String>
}