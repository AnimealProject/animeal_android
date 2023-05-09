package com.epmedu.animeal.profile.data.repository

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants

private val NAME = stringPreferencesKey("name")
val Preferences.name: String
    get() = this[NAME] ?: DefaultConstants.EMPTY_STRING

fun MutablePreferences.updateName(value: String) {
    this[NAME] = value
}

fun MutablePreferences.clearName() {
    this.remove(NAME)
}
