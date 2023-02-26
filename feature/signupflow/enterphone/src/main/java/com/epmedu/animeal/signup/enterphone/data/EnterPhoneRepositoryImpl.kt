package com.epmedu.animeal.signup.enterphone.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberRegionKey
import com.epmedu.animeal.profile.domain.model.Region
import javax.inject.Inject

internal class EnterPhoneRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val authAPI: AuthAPI,
) : EnterPhoneRepository {

    override suspend fun savePhoneNumberAndRegion(
        region: Region,
        phoneNumber: String
    ) {
        dataStore.edit { preferences ->
            preferences[phoneNumberRegionKey] = region.name
            preferences[phoneNumberKey] = phoneNumber
        }
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