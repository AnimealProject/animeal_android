package com.epmedu.animeal.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.extensions.edit
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.data.util.birthDate
import com.epmedu.animeal.profile.data.util.clearBirthDate
import com.epmedu.animeal.profile.data.util.clearEmail
import com.epmedu.animeal.profile.data.util.clearName
import com.epmedu.animeal.profile.data.util.clearPhoneNumber
import com.epmedu.animeal.profile.data.util.clearSurname
import com.epmedu.animeal.profile.data.util.email
import com.epmedu.animeal.profile.data.util.name
import com.epmedu.animeal.profile.data.util.phoneNumber
import com.epmedu.animeal.profile.data.util.phoneNumberRegion
import com.epmedu.animeal.profile.data.util.surname
import com.epmedu.animeal.profile.data.util.updateBirthDate
import com.epmedu.animeal.profile.data.util.updateEmail
import com.epmedu.animeal.profile.data.util.updateName
import com.epmedu.animeal.profile.data.util.updatePhoneNumber
import com.epmedu.animeal.profile.data.util.updatePhoneNumberRegion
import com.epmedu.animeal.profile.data.util.updateSurname
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
        onSuccess: () -> Unit,
    ) {
        authAPI.signOut(onSuccess)
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