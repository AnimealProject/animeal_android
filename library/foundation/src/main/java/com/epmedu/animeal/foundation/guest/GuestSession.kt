package com.epmedu.animeal.foundation.guest

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object GuestSession {
    private val _isGuest = MutableStateFlow(false)
    val isGuest: StateFlow<Boolean> = _isGuest.asStateFlow()

    fun start() {
        _isGuest.value = true
    }

    fun end() {
        _isGuest.value = false
    }
}