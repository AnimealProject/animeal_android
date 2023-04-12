package com.epmedu.animeal.auth

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.cognito.options.AWSCognitoAuthSignInOptions
import com.amplifyframework.auth.cognito.options.AuthFlowType
import com.amplifyframework.auth.exceptions.SessionExpiredException
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Suppress("TooManyFunctions")
class AuthAPI {

    var authenticationType: AuthenticationType = AuthenticationType.Mobile
        private set

    private val AWSCognitoAuthSession.isExpired
        get() = isSignedIn.not() &&
            (
                awsCredentialsResult.error is SessionExpiredException ||
                    identityIdResult.error is SessionExpiredException ||
                    userPoolTokensResult.error is SessionExpiredException ||
                    userSubResult.error is SessionExpiredException
                )

    private val AWSCognitoAuthSession.isSignedInWithoutErrors
        get() = isSignedIn &&
            awsCredentialsResult.type == AuthSessionResult.Type.SUCCESS &&
            identityIdResult.type == AuthSessionResult.Type.SUCCESS &&
            userPoolTokensResult.type == AuthSessionResult.Type.SUCCESS &&
            userSubResult.type == AuthSessionResult.Type.SUCCESS

    suspend fun getCurrentUserId(): String = suspendCancellableCoroutine {
        Amplify.Auth.getCurrentUser(
            { user ->
                resume(user.username)
            },
            { authException ->
                resumeWithException(authException)
            }
        )
    }

    suspend fun isSignedIn(): Boolean {
        return suspendCancellableCoroutine {
            Amplify.Auth.fetchAuthSession(
                { session ->
                    when (session) {
                        is AWSCognitoAuthSession -> {
                            if (session.isExpired) {
                                signOut(
                                    object : AuthRequestHandler {
                                        override fun onSuccess(result: Any?) {
                                            resume(false)
                                        }

                                        override fun onError(exception: Exception) {
                                            resume(false)
                                        }
                                    }
                                )
                            } else {
                                resume(session.isSignedInWithoutErrors)
                            }
                        }
                        else -> {
                            resume(session.isSignedIn)
                        }
                    }
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
            handler::onSuccess
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
