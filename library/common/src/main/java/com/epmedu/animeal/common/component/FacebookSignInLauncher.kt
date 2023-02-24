package com.epmedu.animeal.common.component

interface FacebookSignInLauncher {

    fun signInWithFacebook(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    )
}