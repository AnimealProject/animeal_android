package com.epmedu.animeal.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.extensions.edit
import com.epmedu.animeal.extensions.read
import com.epmedu.animeal.extensions.write
import com.epmedu.animeal.profile.domain.model.BasicProfile
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
) : ProfileRepository {

    override fun getProfile(): Flow<BasicProfile> {
        return dataStore.data.mapNotNull { preferences ->
            preferences.read(PROFILE_DATA_STORE)?.let { json ->
                Gson().fromJson(json, BasicProfile::class.java)
            }
        }
    }

    override fun getBasicProfile(): Flow<BasicProfile> {
        return getProfile().mapNotNull { it }
    }

    override fun saveProfile(profile: BasicProfile): Flow<Unit> {
        return flowOf(Unit).map {
            val json = Gson().toJson(profile, BasicProfile::class.java)
            dataStore.write(PROFILE_DATA_STORE, json)
        }
    }

    override suspend fun updatePhoneAndRegion(phone: String, region: Region) {
        dataStore.data.map {
            val json = it.read(PROFILE_DATA_STORE)
            val profile = Gson().fromJson(json, BasicProfile::class.java) as BasicProfile
            val newProfile = profile.copy(phoneNumber = phone, phoneNumberRegion = region)
            dataStore.write(PROFILE_DATA_STORE, Gson().toJson(newProfile, BasicProfile::class.java))
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
