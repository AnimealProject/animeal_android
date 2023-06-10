package com.epmedu.animeal.auth

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.cognito.options.AWSCognitoAuthSignInOptions
import com.amplifyframework.auth.cognito.options.AuthFlowType
import com.amplifyframework.auth.exceptions.SessionExpiredException
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class AuthAPIImpl : AuthAPI {

    override var authenticationType: AuthenticationType = AuthenticationType.Mobile

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

    override suspend fun getCurrentUserId(): String = suspendCancellableCoroutine {
        Amplify.Auth.getCurrentUser(
            { user ->
                resume(user.username)
            },
            { authException ->
                resumeWithException(authException)
            }
        )
    }

    /** Keep in mind: verification of session expiration (session.isExpired) works only for Mobile authorization flow.
     * Wherever this method is used for Facebook flow, verify that next steps will be ready to handle
     * NotAuthorizedException due to possible refresh token expiration */
    override suspend fun isSignedIn(): Boolean {
        val scope = CoroutineScope(coroutineContext)
        return suspendCancellableCoroutine {
            Amplify.Auth.fetchAuthSession(
                { session ->
                    when (session) {
                        is AWSCognitoAuthSession -> {
                            if (session.isExpired) {
                                scope.launch {
                                    signOut()
                                    resume(false)
                                }
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

    override suspend fun signUp(
        phone: String,
        password: String
    ): ApiResult<Unit> {
        val attrs = mapOf(
            AuthUserAttributeKey.phoneNumber() to phone,
        )

        val options = AuthSignUpOptions.builder()
            .userAttributes(attrs.map { AuthUserAttribute(it.key, it.value) })
            .build()
        return suspendCancellableCoroutine {
            Amplify.Auth.signUp(
                phone,
                password,
                options,
                {
                    resume(ApiResult.Success(Unit))
                },
                {
                    resume(ApiResult.Failure(it))
                }
            )
        }
    }

    override suspend fun signIn(
        phoneNumber: String
    ): ApiResult<Unit> {
        val authSignInOptions = AWSCognitoAuthSignInOptions.builder()
            .authFlowType(AuthFlowType.CUSTOM_AUTH)
            .build()
        return suspendCancellableCoroutine {
            Amplify.Auth.signIn(
                phoneNumber,
                "",
                authSignInOptions,
                {
                    resume(ApiResult.Success(Unit))
                },
                {
                    resume(ApiResult.Failure(it))
                }
            )
        }
    }

    override suspend fun confirmSignIn(
        code: String
    ): ApiResult<AuthSignInResult> {
        return suspendCancellableCoroutine {
            Amplify.Auth.confirmSignIn(
                code,
                {
                    resume(ApiResult.Success(it))
                },
                {
                    resume(ApiResult.Failure(it))
                }
            )
        }
    }

    override fun confirmResendCode(
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

    override suspend fun sendCode(
        phoneNumber: String,
        handler: AuthRequestHandler,
    ) {
        when (authenticationType) {
            AuthenticationType.Mobile -> signIn(phoneNumber)
            is AuthenticationType.Facebook -> sendPhoneCodeByResend(handler)
        }
    }

    override suspend fun signOut() : ApiResult<Unit> {
        return suspendCancellableCoroutine {
            Amplify.Auth.signOut {
                resume(ApiResult.Success<Unit>(Unit))
            }
        }
    }

    private fun sendPhoneCodeByResend(handler: AuthRequestHandler) {
        Amplify.Auth.resendUserAttributeConfirmationCode(
            AuthUserAttributeKey.phoneNumber(),
            handler::onSuccess,
            handler::onError
        )
    }
}