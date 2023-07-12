package com.epmedu.animeal.auth

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import com.epmedu.animeal.token.errorhandler.TokenExpirationHandler
import kotlin.coroutines.resume

class UserAttributesAPIImpl(
    private val errorHandler: TokenExpirationHandler
) : UserAttributesAPI,
    TokenExpirationHandler by errorHandler {

    override suspend fun fetchUserAttributes(): ApiResult<List<AuthUserAttribute>> {
        return suspendCancellableCoroutine {
            Amplify.Auth.fetchUserAttributes(
                { userAttributes ->
                    resume(ApiResult.Success(userAttributes))
                },
                {
                    if (isRefreshTokenHasExpiredException(it)) {
                        handleRefreshTokenExpiration()
                    } else {
                        resume(ApiResult.Failure(it))
                    }
                }
            )
        }
    }

    override suspend fun updateUserAttributes(
        userAttributes: List<AuthUserAttribute>,
    ): ApiResult<Unit> {
        return suspendCancellableCoroutine {
            Amplify.Auth.updateUserAttributes(
                userAttributes,
                {
                    resume(ApiResult.Success(Unit))
                },
                {
                    if (isRefreshTokenHasExpiredException(it)) {
                        handleRefreshTokenExpiration()
                    } else {
                        resume(ApiResult.Failure(it))
                    }
                }
            )
        }
    }

    override suspend fun deleteUser(): ApiResult<Unit> {
        return suspendCancellableCoroutine {
            Amplify.Auth.deleteUser(
                {
                    resume(ApiResult.Success(Unit))
                },
                {
                    if (isRefreshTokenHasExpiredException(it)) {
                        handleRefreshTokenExpiration()
                    } else {
                        resume(ApiResult.Failure(it))
                    }
                }
            )
        }
    }
}