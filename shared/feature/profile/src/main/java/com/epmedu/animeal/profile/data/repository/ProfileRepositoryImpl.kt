package com.epmedu.animeal.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.extensions.edit
import com.epmedu.animeal.profile.data.model.Profile
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
                phoneNumberRegion = preferences.phoneNumberRegion,
                phoneNumber = preferences.phoneNumber,
                email = preferences.email,
                birthDate = preferences.birthDate,
            )
        }
    }

    override fun saveProfile(profile: Profile): Flow<Unit> {
        return flowOf(Unit).map {
            dataStore.edit {
                updatePhoneNumberRegion(profile.phoneNumberRegion)
                updatePhoneNumber(profile.phoneNumber)
                updateName(profile.name)
                updateSurname(profile.surname)
                updateEmail(profile.email)
                updateBirthDate(profile.birthDate)
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
            clearName()
            clearSurname()
            clearPhoneNumber()
            clearEmail()
            clearBirthDate()
        }
    }

    override suspend fun isProfileSaved(): Boolean {
        return with(getProfile().first()) {
            listOf(name, surname, email, birthDate).all { it.isNotEmpty() }
        }
    }
}