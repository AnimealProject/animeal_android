package com.epmedu.animeal.common.constants

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferencesKey {
    val phoneNumberPrefixKey = stringPreferencesKey("phone_number_prefix")
    val phoneNumberKey = stringPreferencesKey("phone_number")
    val nameKey = stringPreferencesKey("name")
    val surnameKey = stringPreferencesKey("surname")
    val emailKey = stringPreferencesKey("email")
    val birthDateKey = stringPreferencesKey("birthdate")
}