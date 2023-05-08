package com.epmedu.animeal.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberRegionKey
import com.epmedu.animeal.common.constants.birthDate
import com.epmedu.animeal.common.constants.clearBirthDate
import com.epmedu.animeal.common.constants.clearEmail
import com.epmedu.animeal.common.constants.clearName
import com.epmedu.animeal.common.constants.email
import com.epmedu.animeal.common.constants.name
import com.epmedu.animeal.common.constants.phoneNumber
import com.epmedu.animeal.common.constants.surname
import com.epmedu.animeal.common.constants.clearPhoneNumber
import com.epmedu.animeal.common.constants.clearSurname
import com.epmedu.animeal.common.constants.updateBirthDate
import com.epmedu.animeal.common.constants.updateEmail
import com.epmedu.animeal.common.constants.updateName
import com.epmedu.animeal.common.constants.updatePhoneNumber
import com.epmedu.animeal.common.constants.updateSurname
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
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
                name = preferences.name,
                surname = preferences.surname,
                phoneNumberRegion = Region.valueOf(preferences[phoneNumberRegionKey] ?: Region.GE.name),
                phoneNumber = preferences.phoneNumber,
                email = preferences.email,
                birthDate = preferences.birthDate,
            )
        }
    }

    override fun saveProfile(profile: Profile): Flow<Unit> {
        return flowOf(Unit).map {
            dataStore.edit { preference ->
                preference[phoneNumberRegionKey] = profile.phoneNumberRegion.name
                preference.updatePhoneNumber(profile.phoneNumber)
                preference.updateName(profile.name)
                preference.updateSurname(profile.surname)
                preference.updateEmail(profile.email)
                preference.updateBirthDate(profile.birthDate)
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
            it.clearName()
            it.clearSurname()
            it.clearPhoneNumber()
            it.clearEmail()
            it.clearBirthDate()
        }
    }

    override suspend fun isProfileSaved(): Boolean {
        return with(getProfile().first()) {
            listOf(name, surname, email, birthDate).all { it.isNotEmpty() }
        }
    }
}