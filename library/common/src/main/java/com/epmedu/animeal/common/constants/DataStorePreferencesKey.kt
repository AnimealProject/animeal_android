package com.epmedu.animeal.common.constants

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferencesKey {
    val phoneNumberKey = stringPreferencesKey("phone_number")
    val firstNameKey = stringPreferencesKey("first_name")
    val lastNameKey = stringPreferencesKey("last_name")
    val emailKey = stringPreferencesKey("email")
    val birthDateKey = stringPreferencesKey("birthdate")
}