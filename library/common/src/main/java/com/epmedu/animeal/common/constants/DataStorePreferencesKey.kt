package com.epmedu.animeal.common.constants

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferencesKey {
    val phoneNumberRegionKey = stringPreferencesKey("phone_number_region")
    val phoneNumberKey = stringPreferencesKey("phone_number")
    val nameKey = stringPreferencesKey("name")
    val surnameKey = stringPreferencesKey("surname")
    val emailKey = stringPreferencesKey("email")
    val birthDateKey = stringPreferencesKey("birthdate")

    val initialGeolocationPermissionKey =
        booleanPreferencesKey("InitialGeolocationPermissionOnHomeScreen")
    val initialCameraPermissionKey = booleanPreferencesKey("InitialCameraPermissionOnHomeScreen")
    val animalType = stringPreferencesKey("AnimalType")
}