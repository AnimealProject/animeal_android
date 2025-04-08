package com.epmedu.animeal.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.di.gson.GsonPreferences
import com.epmedu.animeal.extensions.edit
import com.epmedu.animeal.extensions.read
import com.epmedu.animeal.extensions.write
import com.epmedu.animeal.profile.domain.model.BasicProfile
import com.epmedu.animeal.profile.domain.model.Profile
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

internal class ProfileRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @GsonPreferences private val gson: Gson,
) : ProfileRepository {

    override fun getProfile(): Flow<Profile> {
        return dataStore.data.mapNotNull { preferences ->
            val json = preferences.read(PROFILE_DATA_STORE)
            gson.fromJson(json, Profile::class.java)
        }
    }

    override fun getBasicProfile(): Flow<BasicProfile> {
        return getProfile().mapNotNull { it as? BasicProfile }
    }

    override fun saveProfile(profile: Profile): Flow<Unit> {
        return flowOf(Unit).map {
            val json = gson.toJson(profile, Profile::class.java)
            dataStore.write(PROFILE_DATA_STORE, json)
        }
    }

    override suspend fun updatePhoneAndRegion(phone: String, region: Region) {
        dataStore.data.map {
            val json = it.read(PROFILE_DATA_STORE)
            val profile = gson.fromJson(json, Profile::class.java) as BasicProfile
            val newProfile = profile.copy(phoneNumber = phone, phoneNumberRegion = region)
            dataStore.write(PROFILE_DATA_STORE, gson.toJson(newProfile, Profile::class.java))
        }
    }

    override suspend fun clearProfile() {
        dataStore.edit {
            remove(stringPreferencesKey(PROFILE_DATA_STORE))
        }
    }

    override suspend fun isProfileSaved(): Boolean {
        return with(getBasicProfile().first()) {
            listOf(name, surname, email).all { it.isNotEmpty() }
        }
    }

    companion object {
        const val PROFILE_DATA_STORE = "profile_data_store"
    }
}
