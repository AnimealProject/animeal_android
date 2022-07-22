package com.epmedu.animeal.login.code.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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

    private companion object {
        const val PHONE_NUMBER_PLACEHOLDER = "558 49-99-69"
    }
}