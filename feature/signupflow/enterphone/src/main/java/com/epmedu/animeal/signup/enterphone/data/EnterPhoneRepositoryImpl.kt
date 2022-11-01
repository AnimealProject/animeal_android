package com.epmedu.animeal.signup.enterphone.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.amplifyframework.auth.AuthException.UsernameExistsException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.options.AWSCognitoAuthSignInOptions
import com.amplifyframework.auth.cognito.options.AuthFlowType
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class EnterPhoneRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : EnterPhoneRepository {

    override suspend fun savePhoneNumber(phoneNumber: String) {
        dataStore.edit { preferences ->
            preferences[phoneNumberKey] = phoneNumber
        }
    }

    override suspend fun isPhoneNumberSaved(): Boolean {
        return !dataStore.data
            .map { preferences ->
                preferences[phoneNumberKey]
            }.first().isNullOrEmpty()
    }

    override fun signUpAndSignIn(
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
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
            {
                signIn(phone, onSuccess, onError)
            },
            { error ->
                if (error is UsernameExistsException) {
                    signIn(phone, onSuccess, onError)
                } else {
                    onError()
                }
            }
        )
    }

    override fun signIn(
        phoneNumber: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        val authSignInOptions = AWSCognitoAuthSignInOptions.builder()
            .authFlowType(AuthFlowType.CUSTOM_AUTH)
            .build()

        Amplify.Auth.signIn(
            phoneNumber,
            "",
            authSignInOptions,
            {
                onSuccess()
            },
            {
                onError()
            }
        )
    }
}