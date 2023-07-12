package com.epmedu.animeal.token.refreshtoken

import kotlinx.coroutines.flow.SharedFlow

interface RefreshTokenApi {

    fun getRefreshTokenExpirationFlow(): SharedFlow<Boolean>

    fun markRefreshTokenAsExpired()

    fun confirmRefreshTokenExpirationWasHandled()
}