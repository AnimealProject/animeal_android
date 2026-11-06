package com.epmedu.animeal.push.domain.usecase

import android.util.Log
import com.epmedu.animeal.push.domain.repository.PushRepository
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterDeviceUseCase @Inject constructor(
    private val repository: PushRepository,
) {

    suspend operator fun invoke(tokenValue: String? = null) {
        @Suppress("TooGenericExceptionCaught")
        try {
            val token = tokenValue ?: Firebase.messaging.getToken().await()
            repository.registerDevice(token)
            Log.i("PUSH", "Device registration success")
        } catch (e: Exception) {
            Log.e("PUSH", "Device registration failed", e)
        }
    }
}