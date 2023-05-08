package com.epmedu.animeal.common.constants

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING

/*
import com.epmedu.animeal.profile.domain.model.Region
private val PHONE_NUMBER_REGION = stringPreferencesKey("phone_number_region")
val Preferences.phoneNumberRegion: Region
    get() = this[PHONE_NUMBER_REGION] ?:  Region.GE.name
fun MutablePreferences.updatePhoneNumberRegion(value: String) {
    this[PHONE_NUMBER_REGION] = value
}
*/

private val PHONE_NUMBER = stringPreferencesKey("phone_number")
val Preferences.phoneNumber: String
    get() = this[PHONE_NUMBER] ?: EMPTY_STRING
fun MutablePreferences.updatePhoneNumber(value: String) {
    this[PHONE_NUMBER] = value
}
fun MutablePreferences.clearPhoneNumber() {
    this.remove(PHONE_NUMBER)
}

private val NAME = stringPreferencesKey("name")
val Preferences.name: String
    get() = this[NAME] ?: EMPTY_STRING
fun MutablePreferences.updateName(value: String) {
    this[NAME] = value
}
fun MutablePreferences.clearName() {
    this.remove(NAME)
}

private val SURNAME = stringPreferencesKey("surname")
val Preferences.surname: String
    get() = this[SURNAME] ?: EMPTY_STRING
fun MutablePreferences.updateSurname(value: String) {
    this[SURNAME] = value
}
fun MutablePreferences.clearSurname() {
    this.remove(SURNAME)
}

private val EMAIL = stringPreferencesKey("email")
val Preferences.email: String
    get() = this[EMAIL] ?: EMPTY_STRING
fun MutablePreferences.updateEmail(value: String) {
    this[EMAIL] = value
}
fun MutablePreferences.clearEmail() {
    this.remove(EMAIL)
}

private val BIRTHDATE = stringPreferencesKey("birthdate")
val Preferences.birthDate: String
    get() = this[BIRTHDATE] ?: EMPTY_STRING
fun MutablePreferences.updateBirthDate(value: String) {
    this[BIRTHDATE] = value
}
fun MutablePreferences.clearBirthDate() {
    this.remove(BIRTHDATE)
}

private val ANIMAL_TYPE  = stringPreferencesKey("AnimalType")
val Preferences.animalType: String
    get() = this[ANIMAL_TYPE] ?: EMPTY_STRING
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

object DataStorePreferencesKey {
    val phoneNumberRegionKey = stringPreferencesKey("phone_number_region")
}
