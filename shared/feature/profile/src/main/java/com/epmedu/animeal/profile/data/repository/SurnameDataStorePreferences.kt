package com.epmedu.animeal.profile.data.repository

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants

private val SURNAME = stringPreferencesKey("surname")
val Preferences.surname: String
    get() = this[SURNAME] ?: DefaultConstants.EMPTY_STRING

fun MutablePreferences.updateSurname(value: String) {
    this[SURNAME] = value
}

fun MutablePreferences.clearSurname() {
    this.remove(SURNAME)
}
