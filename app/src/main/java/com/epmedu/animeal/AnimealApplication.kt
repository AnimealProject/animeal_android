package com.epmedu.animeal

import android.app.Application
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
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
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)
        } catch (error: AmplifyException) {
            error(
                "Could not initialize Amplify, please add config to " +
                    "app/src/main/res/raw/amplifyconfiguration.json"
            )
        }
    }
}