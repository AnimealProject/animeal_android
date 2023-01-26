package com.epmedu.animeal.auth

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.core.Amplify

class UserAttributesAPI {

    fun fetchUserAttributes(
        handler: AuthRequestHandler
    ) {
        Amplify.Auth.fetchUserAttributes(
            handler::onSuccess,
            handler::onError,
        )
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