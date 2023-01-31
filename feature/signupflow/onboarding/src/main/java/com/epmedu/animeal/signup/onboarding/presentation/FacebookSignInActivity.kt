package com.epmedu.animeal.signup.onboarding.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.amplifyframework.auth.AuthProvider.facebook
import com.amplifyframework.core.Amplify

class FacebookSignInActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Amplify.Auth.signInWithSocialWebUI(
            facebook(),
            this,
            {
                setResult(RESULT_OK, Intent())
                finish()
            },
            {
                setResult(RESULT_CANCELED, Intent())
                finish()
            }
        )
    }
}