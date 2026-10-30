package com.epmedu.animeal.push.domain.usecase

import android.util.Log
import com.epmedu.animeal.push.domain.repository.PushRepository
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class RegisterDeviceUseCase @Inject constructor(
    private val repository: PushRepository,
) {

    suspend operator fun invoke(tokenValue: String? = null) {
        try {
            val token = tokenValue ?: Firebase.messaging.getToken().await()
            repository.registerDevice(token)
        } catch (e: FirebaseException) {
            Log.e("PUSH", "Fetching FCM registration token failed", e)
        } catch (e: IOException) {
            Log.e("PUSH", "Fetching FCM registration token failed", e)
        }
    }
}