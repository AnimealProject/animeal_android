package com.epmedu.animeal.auth

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.options.AWSCognitoAuthSignInOptions
import com.amplifyframework.auth.cognito.options.AuthFlowType
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify

class AuthAPI {
    fun signUp(
        phone: String,
        password: String,
        handler: AuthRequestHandler,
    ) {
        val attrs = mapOf(
            AuthUserAttributeKey.phoneNumber() to phone,
        )

        val options = AuthSignUpOptions.builder()
            .userAttributes(attrs.map { AuthUserAttribute(it.key, it.value) })
            .build()

        Amplify.Auth.signUp(
            phone,
            password,
            options,
            handler::onSuccess,
            handler::onError,
        )
    }

    fun signIn(
        phoneNumber: String,
        handler: AuthRequestHandler,
    ) {
        val authSignInOptions = AWSCognitoAuthSignInOptions.builder()
            .authFlowType(AuthFlowType.CUSTOM_AUTH)
            .build()

        Amplify.Auth.signIn(
            phoneNumber,
            "",
            authSignInOptions,
            handler::onSuccess,
            handler::onError,
        )
    }

    fun confirmSignIn(
        code: String,
        handler: AuthRequestHandler
    ) {
        Amplify.Auth.confirmSignIn(
            code,
            handler::onSuccess,
            handler::onError,
        )
    }

    fun sendCode(
        phoneNumber: String,
        handler: AuthRequestHandler,
    ) {
        signIn(phoneNumber, handler)
    }

    fun fetchSession(
        handler: AuthRequestHandler
    ) {
        Amplify.Auth.fetchAuthSession(
            handler::onSuccess,
            handler::onError,
        )
    }

    fun signOut(
        handler: AuthRequestHandler
    ) {
        Amplify.Auth.signOut(
            handler::onSuccess,
            handler::onError,
        )
    }
}
