package com.epmedu.animeal.auth

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import kotlin.coroutines.resume

class UserAttributesAPI {

    suspend fun fetchUserAttributes(): ApiResult<List<AuthUserAttribute>> {
        return suspendCancellableCoroutine {
            Amplify.Auth.fetchUserAttributes(
                { userAttributes ->
                    resume(ApiResult.Success(userAttributes))
                },
                {
                    resume(ApiResult.Failure(it))
                }
            )
        }
    }

    suspend fun updateUserAttributes(
        userAttributes: List<AuthUserAttribute>,
    ): ApiResult<Unit> {
        return suspendCancellableCoroutine {
            Amplify.Auth.updateUserAttributes(
                userAttributes,
                {
                    resume(ApiResult.Success(Unit))
                },
                {
                    resume(ApiResult.Failure(it))
                }
            )
        }
    }

    suspend fun deleteUser(): ApiResult<Unit> {
        return suspendCancellableCoroutine {
            Amplify.Auth.deleteUser(
                {
                    resume(ApiResult.Success(Unit))
                },
                {
                    resume(ApiResult.Failure(it))
                }
            )
        }
    }
}