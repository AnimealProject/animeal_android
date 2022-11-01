package com.epmedu.animeal.signup.entercode.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.amplifyframework.auth.cognito.options.AWSCognitoAuthSignInOptions
import com.amplifyframework.auth.cognito.options.AuthFlowType
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class EnterCodeRepositoryImpl @Inject constructor(
    dataStore: DataStore<Preferences>
) : EnterCodeRepository {

    override val phoneNumber: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[phoneNumberKey] ?: PHONE_NUMBER_PLACEHOLDER
        }

    override fun sendCode(
        phoneNumber: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
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

    override fun confirmCode(
        code: List<Int?>,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        Amplify.Auth.confirmSignIn(
            code.joinToString(""),
            {
                onSuccess()
            },
            {
                onError()
            }
        )
    }

    private companion object {
        const val PHONE_NUMBER_PLACEHOLDER = "558 49-99-69"
    }
}