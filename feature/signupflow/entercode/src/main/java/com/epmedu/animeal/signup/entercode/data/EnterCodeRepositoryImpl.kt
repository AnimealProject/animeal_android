package com.epmedu.animeal.signup.entercode.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberPrefixKey
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.common.constants.DefaultConstants.PHONE_NUMBER_PREFIX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class EnterCodeRepositoryImpl @Inject constructor(
    dataStore: DataStore<Preferences>,
    private val authAPI: AuthAPI,
) : EnterCodeRepository {

    override val phoneNumberWithPrefix: Flow<String> = dataStore.data
        .map { preferences ->
            val prefix = preferences[phoneNumberPrefixKey] ?: PHONE_NUMBER_PREFIX
            val phoneNumber = preferences[phoneNumberKey] ?: EMPTY_STRING
            prefix + phoneNumber
        }

    override suspend fun sendCodeBySignIn(requestHandler: AuthRequestHandler) {
        authAPI.signIn(phoneNumberWithPrefix.first(), requestHandler)
    }

    override suspend fun sendCodeByResend(requestHandler: AuthRequestHandler) {
        authAPI.sendCode(requestHandler)
    }

    override fun confirmSignInCode(
        code: List<Int?>,
        requestHandler: AuthRequestHandler
    ) {
        authAPI.confirmSignIn(code.joinToString(""), requestHandler)
    }

    override fun confirmResendCode(
        code: List<Int?>,
        requestHandler: AuthRequestHandler
    ) {
        authAPI.confirmPhoneNumber(code.joinToString(""), requestHandler)
    }
}