package com.epmedu.animeal.signup.enterphone.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class EnterPhoneRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val authAPI: AuthAPI,
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

    override fun signUp(
        phone: String,
        password: String,
        requestHandler: AuthRequestHandler
    ) {
        authAPI.signUp(phone, password, requestHandler)
    }

    override fun signIn(
        phoneNumber: String,
        requestHandler: AuthRequestHandler,
    ) {
        authAPI.signIn(phoneNumber, requestHandler)
    }
}