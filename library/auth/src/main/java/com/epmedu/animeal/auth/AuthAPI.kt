package com.epmedu.animeal.auth

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.cognito.options.AWSCognitoAuthSignInOptions
import com.amplifyframework.auth.cognito.options.AuthFlowType
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AuthAPI {

    var authenticationType: AuthenticationType = AuthenticationType.Mobile
        private set

    val currentUserId get() = Amplify.Auth.currentUser.userId

    private val AWSCognitoAuthSession.isSignedInWithoutErrors
        get() = isSignedIn &&
            awsCredentials.type == AuthSessionResult.Type.SUCCESS &&
            identityId.type == AuthSessionResult.Type.SUCCESS &&
            userPoolTokens.type == AuthSessionResult.Type.SUCCESS &&
            userSub.type == AuthSessionResult.Type.SUCCESS

    suspend fun isSignedIn(): Boolean {
        return suspendCancellableCoroutine {
            Amplify.Auth.fetchAuthSession(
                { session ->
                    resume(
                        when (session) {
                            is AWSCognitoAuthSession -> session.isSignedInWithoutErrors
                            else -> session.isSignedIn
                        }
                    )
                },
                {
                    resume(false)
                }
            )
        }
    }

    fun setMobileAuthenticationType() {
        authenticationType = AuthenticationType.Mobile
    }

    fun setFacebookAuthenticationType(isPhoneNumberVerified: Boolean) {
        authenticationType = AuthenticationType.Facebook(isPhoneNumberVerified)
    }

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

    fun confirmResendCode(
        code: String,
        handler: AuthRequestHandler
    ) {
        Amplify.Auth.confirmUserAttribute(
            AuthUserAttributeKey.phoneNumber(),
            code,
            handler::onSuccess,
            handler::onError
        )
    }

    fun sendCode(
        phoneNumber: String,
        handler: AuthRequestHandler,
    ) {
        when (authenticationType) {
            AuthenticationType.Mobile -> signIn(phoneNumber, handler)
            is AuthenticationType.Facebook -> sendPhoneCodeByResend(handler)
        }
    }

    fun signOut(
        handler: AuthRequestHandler
    ) {
        Amplify.Auth.signOut(
            handler::onSuccess,
            handler::onError,
        )
    }

    private fun sendPhoneCodeByResend(handler: AuthRequestHandler) {
        Amplify.Auth.resendUserAttributeConfirmationCode(
            AuthUserAttributeKey.phoneNumber(),
            handler::onSuccess,
            handler::onError
        )
    }
}
