package com.epmedu.animeal.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.birthDateKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.emailKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.nameKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberRegionKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.surnameKey
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.domain.model.Region
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ProfileRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val authAPI: AuthAPI,
) : ProfileRepository {

    override fun getProfile(): Flow<Profile> {
        return dataStore.data.map { preferences ->
            Profile(
                name = preferences[nameKey] ?: EMPTY_STRING,
                surname = preferences[surnameKey] ?: EMPTY_STRING,
                phoneNumberRegion = Region.valueOf(preferences[phoneNumberRegionKey] ?: Region.GE.name),
                phoneNumber = preferences[phoneNumberKey] ?: EMPTY_STRING,
                email = preferences[emailKey] ?: EMPTY_STRING,
                birthDate = preferences[birthDateKey] ?: EMPTY_STRING,
            )
        }
    }

    override fun saveProfile(profile: Profile): Flow<Unit> {
        return flowOf(Unit).map {
            dataStore.edit { preference ->
                preference[nameKey] = profile.name
                preference[surnameKey] = profile.surname
                preference[phoneNumberRegionKey] = profile.phoneNumberRegion.name
                preference[phoneNumberKey] = profile.phoneNumber
                preference[emailKey] = profile.email
                preference[birthDateKey] = profile.birthDate
            }
        }
    }

    override suspend fun logOut(
        authRequestHandler: AuthRequestHandler
    ) {
        authAPI.signOut(authRequestHandler)
    }

    override suspend fun clearProfile() {
        dataStore.edit {
            it.remove(nameKey)
            it.remove(surnameKey)
            it.remove(phoneNumberKey)
            it.remove(emailKey)
            it.remove(birthDateKey)
        }
    }

    override suspend fun isProfileSaved(): Boolean {
        return with(getProfile().first()) {
            listOf(name, surname, phoneNumber, email, birthDate).all { it.isNotEmpty() }
        }
    }
}