package com.epmedu.animeal

import android.util.Log
import com.epmedu.animeal.push.domain.usecase.RegisterDeviceUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFirebaseMessagingService(
    private val registerDeviceUseCase: RegisterDeviceUseCase
) : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("Push", "Message received: ${remoteMessage.data}")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            registerDeviceUseCase(token)
        }
    }
}