package com.epmedu.animeal.profile.data.repository

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants

private val BIRTHDATE = stringPreferencesKey("birthdate")
val Preferences.birthDate: String
    get() = this[BIRTHDATE] ?: DefaultConstants.EMPTY_STRING

fun MutablePreferences.updateBirthDate(value: String) {
    this[BIRTHDATE] = value
}

fun MutablePreferences.clearBirthDate() {
    this.remove(BIRTHDATE)
}
