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

    fun updateUserAttributes(
        userAttributes: List<AuthUserAttribute>,
        handler: AuthRequestHandler
    ) {
        Amplify.Auth.updateUserAttributes(
            userAttributes,
            handler::onSuccess,
            handler::onError,
        )
    }

    fun deleteUser(
        handler: AuthRequestHandler
    ) {
        Amplify.Auth.deleteUser(
            handler::onSuccess,
            handler::onError,
        )
    }
}