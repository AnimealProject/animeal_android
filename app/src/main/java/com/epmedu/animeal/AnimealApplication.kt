package com.epmedu.animeal

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnimealApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        configureAmplify()
    }

    private fun configureAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Log.i(APPLICATION_TAG, "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e(APPLICATION_TAG, "Could not initialize Amplify", error)
        }
    }

    private companion object {
        private const val APPLICATION_TAG = "AnimealApplication"
    }
}