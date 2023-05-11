package com.epmedu.animeal.extensions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

suspend inline fun DataStore<Preferences>.edit(
    noinline transform: suspend MutablePreferences.() -> Unit
) = edit(transform)