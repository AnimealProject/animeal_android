package com.epmedu.animeal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.component.FacebookSignInLauncher
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.TransparentSystemUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    ComponentActivity(),
    FacebookSignInLauncher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AnimealTheme {
                TransparentSystemUi {
                    MainHost()
                }
            }
        }
    }

    override fun signInWithFacebook(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Amplify.Auth.signInWithSocialWebUI(
            AuthProvider.facebook(),
            this,
            { signInResult ->
                if (signInResult.isSignInComplete) onSuccess()
            },
            onError
        )
    }
}
