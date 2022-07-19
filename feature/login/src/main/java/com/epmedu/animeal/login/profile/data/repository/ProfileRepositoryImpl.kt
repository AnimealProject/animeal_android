package com.epmedu.animeal.login.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
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
                firstName = preferences[FIRST_NAME] ?: EMPTY_STRING,
                lastName = preferences[LAST_NAME] ?: EMPTY_STRING,
                email = preferences[EMAIL] ?: EMPTY_STRING,
                birthDate = preferences[BIRTH_DATE] ?: EMPTY_STRING,
            )
        }
    }

    override suspend fun saveProfile(profile: Profile): Flow<Unit> {
        return flowOf(Unit).map {
            dataStore.edit { preference ->
                preference[FIRST_NAME] = profile.firstName
                preference[LAST_NAME] = profile.lastName
                preference[EMAIL] = profile.email
                preference[BIRTH_DATE] = profile.birthDate
            }
        }
    }

    private companion object {
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val EMAIL = stringPreferencesKey("email")
        val BIRTH_DATE = stringPreferencesKey("birthdate")
        const val EMPTY_STRING = ""
    }
}