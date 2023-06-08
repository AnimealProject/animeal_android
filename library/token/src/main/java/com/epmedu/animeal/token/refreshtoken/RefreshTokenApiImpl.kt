package com.epmedu.animeal.token.refreshtoken

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RefreshTokenApiImpl : RefreshTokenApi {

    private val refreshTokenExpirationFlow = MutableStateFlow<Boolean>(false)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getRefreshTokenExpirationFlow(): StateFlow<Boolean> = refreshTokenExpirationFlow.asStateFlow()

    override fun markRefreshTokenAsExpired() {
        coroutineScope.launch {
            refreshTokenExpirationFlow.emit(true)
        }
    }

    override fun confirmRefreshTokenExpirationWasHandled() {
        coroutineScope.launch { refreshTokenExpirationFlow.emit(false) }
    }
}