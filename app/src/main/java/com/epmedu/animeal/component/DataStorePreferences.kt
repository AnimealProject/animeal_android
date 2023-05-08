package com.epmedu.animeal.component

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants

private val ANIMAL_TYPE  = stringPreferencesKey("AnimalType")
val Preferences.animalType: String
    get() = this[ANIMAL_TYPE] ?: DefaultConstants.EMPTY_STRING
fun MutablePreferences.updateAnimalType(value: String) {
    this[ANIMAL_TYPE] = value
}
fun MutablePreferences.clearAnimalType() {
    this.remove(ANIMAL_TYPE)
}

private val INITIAL_GEOLOCATION_PERMISSION = booleanPreferencesKey("InitialGeolocationPermissionOnHomeScreen")
val Preferences.initialGeolocationPermission: Boolean
    get() = this[INITIAL_GEOLOCATION_PERMISSION] ?: false
fun MutablePreferences.updateInitialGeolocationPermission(value: Boolean) {
    this[INITIAL_GEOLOCATION_PERMISSION] = value
}
fun MutablePreferences.clearInitialGeolocationPermission() {
    this.remove(INITIAL_GEOLOCATION_PERMISSION)
}

private val INITIAL_CAMERA_PERMISSION  = booleanPreferencesKey("InitialCameraPermissionOnHomeScreen")
val Preferences.initialCameraPermission: Boolean
    get() = this[INITIAL_CAMERA_PERMISSION] ?: false
fun MutablePreferences.updateInitialCameraPermission(value: Boolean) {
    this[INITIAL_CAMERA_PERMISSION] = value
}
fun MutablePreferences.clearInitialCameraPermission() {
    this.remove(INITIAL_CAMERA_PERMISSION)
}
