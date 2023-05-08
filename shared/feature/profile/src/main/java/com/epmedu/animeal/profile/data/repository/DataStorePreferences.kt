package com.epmedu.animeal.profile.data.repository

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.profile.domain.model.Region

private val PHONE_NUMBER_REGION = stringPreferencesKey("phone_number_region")
val Preferences.phoneNumberRegion: Region
    get() = (this[PHONE_NUMBER_REGION] ?: Region.GE.name) as Region
fun MutablePreferences.updatePhoneNumberRegion(value: Region) {
    this[PHONE_NUMBER_REGION] = value.name
}

private val PHONE_NUMBER = stringPreferencesKey("phone_number")
val Preferences.phoneNumber: String
    get() = this[PHONE_NUMBER] ?: DefaultConstants.EMPTY_STRING
fun MutablePreferences.updatePhoneNumber(value: String) {
    this[PHONE_NUMBER] = value
}
fun MutablePreferences.clearPhoneNumber() {
    this.remove(PHONE_NUMBER)
}

private val NAME = stringPreferencesKey("name")
val Preferences.name: String
    get() = this[NAME] ?: DefaultConstants.EMPTY_STRING
fun MutablePreferences.updateName(value: String) {
    this[NAME] = value
}
fun MutablePreferences.clearName() {
    this.remove(NAME)
}

private val SURNAME = stringPreferencesKey("surname")
val Preferences.surname: String
    get() = this[SURNAME] ?: DefaultConstants.EMPTY_STRING
fun MutablePreferences.updateSurname(value: String) {
    this[SURNAME] = value
}
fun MutablePreferences.clearSurname() {
    this.remove(SURNAME)
}

private val EMAIL = stringPreferencesKey("email")
val Preferences.email: String
    get() = this[EMAIL] ?: DefaultConstants.EMPTY_STRING
fun MutablePreferences.updateEmail(value: String) {
    this[EMAIL] = value
}
fun MutablePreferences.clearEmail() {
    this.remove(EMAIL)
}

private val BIRTHDATE = stringPreferencesKey("birthdate")
val Preferences.birthDate: String
    get() = this[BIRTHDATE] ?: DefaultConstants.EMPTY_STRING
fun MutablePreferences.updateBirthDate(value: String) {
    this[BIRTHDATE] = value
}
fun MutablePreferences.clearBirthDate() {
    this.remove(BIRTHDATE)
}
