package com.epmedu.animeal.component

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants

private val ANIMAL_TYPE = stringPreferencesKey("AnimalType")
private val INITIAL_CAMERA_PERMISSION = booleanPreferencesKey("InitialCameraPermissionOnHomeScreen")
private val IS_GEOLOCATION_PERMISSION_RATIONALE_SHOWN = booleanPreferencesKey(
    "is_geolocation_permission_rationale_shown"
)

val Preferences.animalType: String
    get() = this[ANIMAL_TYPE] ?: DefaultConstants.EMPTY_STRING

val Preferences.initialCameraPermission: Boolean
    get() = this[INITIAL_CAMERA_PERMISSION] ?: false

val Preferences.isGeolocationPermissionRationaleShown: Boolean
    get() = this[IS_GEOLOCATION_PERMISSION_RATIONALE_SHOWN] ?: false

fun MutablePreferences.updateAnimalType(value: String) {
    this[ANIMAL_TYPE] = value
}

fun MutablePreferences.updateInitialCameraPermission(value: Boolean) {
    this[INITIAL_CAMERA_PERMISSION] = value
}

fun MutablePreferences.updateIsGeolocationPermissionRationaleShown(value: Boolean) {
    this[IS_GEOLOCATION_PERMISSION_RATIONALE_SHOWN] = value
}