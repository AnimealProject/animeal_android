package com.epmedu.animeal.token.errorhandler

import com.amplifyframework.api.ApiException.ApiAuthException
import com.amplifyframework.api.ApiException.NonRetryableException
import com.amplifyframework.auth.cognito.exceptions.configuration.InvalidUserPoolConfigurationException
import com.amplifyframework.auth.exceptions.NotAuthorizedException
import com.amplifyframework.auth.exceptions.SignedOutException
import com.epmedu.animeal.token.refreshtoken.RefreshTokenApi

internal class TokenExpirationHandlerImpl(
    private val tokenApi: RefreshTokenApi
) : TokenExpirationHandler {

    override fun handleRefreshTokenExpiration() {
        tokenApi.markRefreshTokenAsExpired()
    }

    override fun isRefreshTokenHasExpiredException(exception: Throwable): Boolean {
        return exception is NotAuthorizedException ||
            exception is SignedOutException ||
            exception is NonRetryableException ||
            exception.cause is ApiAuthException ||
            exception is InvalidUserPoolConfigurationException
    }
}