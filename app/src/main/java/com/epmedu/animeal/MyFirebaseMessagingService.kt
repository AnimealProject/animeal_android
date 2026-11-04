package com.epmedu.animeal

import android.util.Log
import android.widget.Toast
import com.epmedu.animeal.push.domain.usecase.RegisterDeviceUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Suppress("LateinitUsage")
    @Inject
    lateinit var registerDeviceUseCase: RegisterDeviceUseCase

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("Push", "Action received: ${remoteMessage.data.get("action")}")
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                applicationContext,
                remoteMessage.notification?.body,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            registerDeviceUseCase(token)
        }
    }
}