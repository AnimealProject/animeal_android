package com.epmedu.animeal.signup.entercode.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class EnterCodeRepositoryImpl @Inject constructor(
    dataStore: DataStore<Preferences>,
    private val authAPI: AuthAPI,
) : EnterCodeRepository {

    override val phoneNumber: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[phoneNumberKey] ?: ""
        }

    override suspend fun sendCode(requestHandler: AuthRequestHandler) {
        authAPI.sendCode(phoneNumber.first(), requestHandler)
    }

    override fun confirmCode(
        code: List<Int?>,
        requestHandler: AuthRequestHandler
    ) {
        authAPI.confirmSignIn(code.joinToString(""), requestHandler)
    }
}