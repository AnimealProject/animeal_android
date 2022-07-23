package com.epmedu.animeal.login.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.birthDateKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.emailKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.firstNameKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.lastNameKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import com.epmedu.animeal.common.constants.Text.EMPTY_STRING
import com.epmedu.animeal.login.profile.data.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ProfileRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ProfileRepository {

    override suspend fun getProfile(): Flow<Profile> {
        return dataStore.data.map { preferences ->
            Profile(
                firstName = preferences[firstNameKey] ?: EMPTY_STRING,
                lastName = preferences[lastNameKey] ?: EMPTY_STRING,
                phoneNumber = preferences[phoneNumberKey] ?: EMPTY_STRING,
                email = preferences[emailKey] ?: EMPTY_STRING,
                birthDate = preferences[birthDateKey] ?: EMPTY_STRING,
            )
        }
    }

    override suspend fun saveProfile(profile: Profile): Flow<Unit> {
        return flowOf(Unit).map {
            dataStore.edit { preference ->
                preference[firstNameKey] = profile.firstName
                preference[lastNameKey] = profile.lastName
                preference[emailKey] = profile.email
                preference[birthDateKey] = profile.birthDate
            }
        }
    }
}