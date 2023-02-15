package com.epmedu.animeal.common.constants

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferencesKey {
    val phoneNumberRegionKey = stringPreferencesKey("phone_number_region")
    val phoneNumberKey = stringPreferencesKey("phone_number")
    val phoneNumberVerifiedKey = stringPreferencesKey("phone_number_verified")
    val nameKey = stringPreferencesKey("name")
    val surnameKey = stringPreferencesKey("surname")
    val emailKey = stringPreferencesKey("email")
    val birthDateKey = stringPreferencesKey("birthdate")
}