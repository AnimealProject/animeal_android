package com.epmedu.animeal.login.phone.data

import androidx.compose.ui.text.AnnotatedString
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import com.epmedu.animeal.foundation.input.PhoneFormatTransformation
import javax.inject.Inject

internal class EnterPhoneRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : EnterPhoneRepository {

    override suspend fun savePhoneNumberAndSendCode(phoneNumber: String) {
        savePhoneNumber(phoneNumber)
        // TODO: Implement SMS sending
    }

    private suspend fun savePhoneNumber(phoneNumber: String) {
        dataStore.edit { preferences ->
            preferences[phoneNumberKey] = phoneNumber.formatNumber()
        }
    }

    private fun String.formatNumber(): String {
        return PhoneFormatTransformation.filter(AnnotatedString(this)).text.text
    }
}