package com.epmedu.animeal.token.errorhandler

interface TokenExpirationHandler {

    fun handleRefreshTokenExpiration()

    fun isRefreshTokenHasExpiredException(exception: Throwable): Boolean
}