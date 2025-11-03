package com.epmedu.animeal

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("Push", "Message received: ${remoteMessage.data}")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        /*Amplify.Notifications.Push.registerDevice(
            token,
            {
                Amplify.Auth.getCurrentUser(
                    {
                        Amplify.Notifications.Push.identifyUser(
                            it.userId,
                            { Log.i("Push", "User identified: $token") },
                            { Log.e("PushError", "Failed to identify user", it) }
                        )
                    },
                    { Log.e("Auth", "Error getting current user", it) }
                )
            },
            { Log.e("PushError", "Failed to register device", it) }
        )*/
    }
}