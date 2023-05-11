package com.epmedu.animeal.profile.data.util

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.profile.domain.model.Region

private val PHONE_NUMBER_REGION = stringPreferencesKey("phone_number_region")
val Preferences.phoneNumberRegion: Region
    get() = Region.valueOf(this[PHONE_NUMBER_REGION] ?: Region.GE.name)

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
