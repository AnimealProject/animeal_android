package com.epmedu.animeal.component

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants

private val ANIMAL_TYPE = stringPreferencesKey("AnimalType")
private val INITIAL_CAMERA_PERMISSION = booleanPreferencesKey("InitialCameraPermissionOnHomeScreen")
private val IS_GEOLOCATION_PERMISSION_RATIONALE_SHOWN = booleanPreferencesKey(
    "is_geolocation_permission_rationale_shown"
)
private val VIEWED_FEEDING_IDS = stringSetPreferencesKey("viewed_feedings_ids")

val Preferences.animalType: String
    get() = this[ANIMAL_TYPE] ?: DefaultConstants.EMPTY_STRING

val Preferences.initialCameraPermission: Boolean
    get() = this[INITIAL_CAMERA_PERMISSION] ?: false

val Preferences.isGeolocationPermissionRationaleShown: Boolean
    get() = this[IS_GEOLOCATION_PERMISSION_RATIONALE_SHOWN] ?: false

val Preferences.viewedFeedingIds: Set<String>
    get() = this[VIEWED_FEEDING_IDS] ?: emptySet()

fun MutablePreferences.updateAnimalType(value: String) {
    this[ANIMAL_TYPE] = value
}

fun MutablePreferences.updateInitialCameraPermission(value: Boolean) {
    this[INITIAL_CAMERA_PERMISSION] = value
}

fun MutablePreferences.updateIsGeolocationPermissionRationaleShown(value: Boolean) {
    this[IS_GEOLOCATION_PERMISSION_RATIONALE_SHOWN] = value
}

fun MutablePreferences.updateViewedFeedingIds(feedingIds: Set<String>) {
    this[VIEWED_FEEDING_IDS] = feedingIds
}