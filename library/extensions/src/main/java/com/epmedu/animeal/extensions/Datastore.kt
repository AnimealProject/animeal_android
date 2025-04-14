package com.epmedu.animeal.extensions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

suspend inline fun DataStore<Preferences>.edit(
    noinline transform: suspend MutablePreferences.() -> Unit
) = edit(transform)

suspend inline fun DataStore<Preferences>.write(key: String, data: String) {
    edit { it[stringPreferencesKey(key)] = data }
}

fun Preferences.read(key: String, defaultValue: String? = null): String? {
    return get(stringPreferencesKey(key)) ?: defaultValue
}
