package com.epmedu.animeal.profile.data.repository

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants

private val EMAIL = stringPreferencesKey("email")
val Preferences.email: String
    get() = this[EMAIL] ?: DefaultConstants.EMPTY_STRING

fun MutablePreferences.updateEmail(value: String) {
    this[EMAIL] = value
}

fun MutablePreferences.clearEmail() {
    this.remove(EMAIL)
}
