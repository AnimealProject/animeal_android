package com.epmedu.animeal.login.profile.data.storage

import android.content.SharedPreferences
import com.epmedu.animeal.login.profile.data.model.ProfileObj
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

internal class ProfileStorage @Inject constructor(
    private val sp: SharedPreferences
) {
    private val gson by lazy { Gson() }

    val profileFlow = MutableStateFlow(ProfileObj())

    private val profile: ProfileObj
        get() {
            val jsonProfile = sp.getString(PROFILE, EMPTY_STRING)
            return gson.fromJson(jsonProfile, ProfileObj::class.java) ?: ProfileObj()
        }

    init {
        profileFlow.value = profile
    }

    fun saveProfile(profile: ProfileObj) {
        val jsonProfile = gson.toJson(profile)
        sp.edit().putString(PROFILE, jsonProfile).apply()
        profileFlow.value = profile
    }

    fun clearProfile() {
        sp.edit().putString(PROFILE, EMPTY_STRING).apply()
    }

    private companion object {
        const val PROFILE = "profile"
        const val EMPTY_STRING = ""
    }
}